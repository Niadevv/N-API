package co.uk.niadel.mpi.modhandler.loadhandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.ReportedException;
import org.apache.commons.io.IOUtils;
import co.uk.niadel.mpi.annotations.AnnotationHandlerRegistry;
import co.uk.niadel.mpi.annotations.IAnnotationHandler;
import co.uk.niadel.mpi.annotations.VersionMarkingAnnotations.TestFeature;
import co.uk.niadel.mpi.annotations.MPIAnnotations.*;
import co.uk.niadel.mpi.client.resources.ResourcesRegistry;
import co.uk.niadel.mpi.exceptions.ModDependencyNotFoundException;
import co.uk.niadel.mpi.exceptions.OutdatedLibraryException;
import co.uk.niadel.mpi.modhandler.IModRegister;
import co.uk.niadel.mpi.potions.PotionRegistry;
import co.uk.niadel.mpi.rendermanager.RenderRegistry;
import co.uk.niadel.mpi.util.NAPILogHelper;
import co.uk.niadel.mpi.util.ParseUtils;

/**
 * This isn't actually as flexible as the Forge mod loader, but it does most of the
 * same stuff. Flexibility may be improved in a later version of N-API.
 * 
 * TODO Remove the need of the ModID.modid file.
 * 
 * This is very System.gc() intensive to keep resources used at a minimum.
 * 
 * @author Niadel
 *
 */
@TestFeature(stable = false, firstAppearance = "1.0")
public class NModLoader extends URLClassLoader
{
	private static final NModLoader instance = new NModLoader(new URL[0]);
	
	public NModLoader(URL[] urls)
	{
		super(urls, NModLoader.class.getClassLoader());
	}

	/**
	 * The Minecraft object.
	 */
	public static Minecraft theMinecraft = Minecraft.getMinecraft();
	
	/**
	 * The MC profiler.
	 */
	public static Profiler mcProfiler = theMinecraft.mcProfiler;
	
	/**
	 * List of modids that have been found and are scheduled to be loaded/have been loaded.
	 */
	public static List<Mod> mods = new ArrayList<>();
	
	/**
	 * A list of modids belonging to Forge mods, added so N-API mods can test for Forge mods.
	 */
	public static List<String> forgeModids = new ArrayList<>();
	
	/**
	 * A list of library classes, or classes marked with the @Library annotation.
	 */
	public static List<IModRegister> modLibraries = new ArrayList<>();
	
	/**
	 * The main Minecraft directory.
	 */
	public static File mcMainDir = theMinecraft.mcDataDir;
	
	/**
	 * The directory for mods to be put in, the same folder Forge uses.
	 */
	public static File mcModsDir = new File(theMinecraft.mcDataDir + File.separator + "mods" + File.separator);
	
	/**
	 * Where the decompressed mod zip files are copied to for later loading.
	 */
	public static File actModsDir = new File(mcModsDir + File.separator + "act_mods" + File.separator);
	
	/**
	 * Keyed by the mod's modId, valued by the modId's main class object.
	 */
	public static Map<String, Mod> modIds = new HashMap<>();
	
	/**
	 * Methods to execute on preInit.
	 */
	public static Map<IModRegister, Method> preInitMethods = new HashMap<>();
	
	/**
	 * Methods to execute on init.
	 */
	public static Map<IModRegister, Method> initMethods = new HashMap<>();
	
	/**
	 * Methods to execute on postInit.
	 */
	public static Map<IModRegister, Method> postInitMethods = new HashMap<>();

	/**
	 * Looks for whether or not the mod with the specified modId exists.
	 * @param modId
	 * @return Whether the mod exists.
	 */
	public static final boolean doesModExist(String modId)
	{
		if (modIds.containsKey(modId))
		{
			return mods.contains(modIds.get(modId));
		}
		else if (forgeModids.contains(modId))
		{
			//For Forge compat.
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Looks for whether or not the library with the specified modId exists.
	 * @param modId
	 * @return Whether the library exists.
	 */
	public static final boolean doesLibraryExist(String modId)
	{
		if (modIds.containsKey(modId))
		{
			return modLibraries.contains(modId);
		}
		else
		{
			return false;
		}
	}
	
	@Internal
	private final void loadClass(String className, byte[] bytes)
	{
		super.defineClass(className, bytes, 0, bytes.length);
	}
	
	public static final void defineClass(String className, byte[] bytes)
	{
		instance.loadClass(className, bytes);
	}
	
	/**
	 * Adds a URL into the class path.
	 * @param url
	 */
	private final void addUrl(URL url)
	{
		super.addURL(url);
	}
	
	/**
	 * Loads a URL into the class path.
	 * @param url
	 */
	public static final void loadUrl(URL url)
	{
		instance.addURL(url);
	}
	
	/**
	 * Gets a Mod object by it's mod id.
	 * @param modId
	 * @return
	 */
	public static final Mod getModByModId(String modId)
	{
		return modIds.get(modId);
	}
	
	/**
	 * The entry point for the loader.
	 * 
	 * @throws ZipException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchFieldException
	 * @throws InstantiationException
	 */
	public static final void loadModsFromDir()
	{
		try
		{
			if (!mcModsDir.exists())
			{
				mcModsDir.mkdir();
				NAPILogHelper.log("Created mods folder! This user has not used mods folder mods before.");
			}
			
			if (!actModsDir.exists())
			{
				actModsDir.mkdirs();
				NAPILogHelper.logWarn("Please note it will take longer to load N-API with lots of mods as they need to be prepared for loading first! If you only have one mod, ignore this!");
			}
			
			loadUrl(actModsDir.toURI().toURL());
			
			initNAPIRegister((Class<? extends IModRegister>) Class.forName("co.uk.niadel.mpi.modhandler.ModRegister"));

			if (mcModsDir.listFiles() != null)
			{
				for (File currFile : mcModsDir.listFiles())
				{
					File nextLoad = extractFromZip(new ZipFile(currFile));
					//Just in case.
					loadUrl(nextLoad.toURI().toURL());
					loadClasses(nextLoad);
				}
			}

			System.gc();
		}
		catch (IOException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | InstantiationException e)
		{
			NAPILogHelper.logError(e);
		}
	}
	
	/**
	 * Calls all mod register's registerTransformers() method.
	 */
	public static final void registerTransformers()
	{
		Iterator modsObjectIterator = mods.iterator();
		Iterator<IModRegister> modsLibraryIterator = modLibraries.iterator();
		
		while (modsObjectIterator.hasNext())
		{
			IModRegister currRegister = (IModRegister) modsObjectIterator.next();
			currRegister.registerTransformers();
		}
		
		while (modsLibraryIterator.hasNext())
		{
			//Generics for the win!
			modsLibraryIterator.next().registerTransformers();
		}
	}
	
	/**
	 * Only ever used to load the N-API ModRegister, so the regular checks are ignored. Don't ever call this outside of
	 * the loader even though you have the ability to with the Reflection stuff.
	 * @param theClass
	 */
	private static final void initNAPIRegister(Class<? extends IModRegister> theClass)
	{
		try
		{
			IModRegister register = theClass.newInstance();
			processAnnotations(new Mod(register));
			register.preModInit();
			register.modInit();
			register.postModInit();
		}
		catch (SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException | ClassNotFoundException e)
		{
			//Crash the game - Failure to load the N-API register can break a LOT of stuff.
			CrashReport crashReport = CrashReport.makeCrashReport(e, "Loading N-API ModRegister");
			crashReport.makeCategory("Initialising N-API");
			throw new ReportedException(crashReport);
		}
	}
	
	/**
	 * Extracts the file for loading later on. Returns the file where the
	 * extracted files are moved for convenience in loading.
	 * 
	 * @param zip
	 * @return outputDir
	 * @throws IOException
	 */
	public static final File extractFromZip(ZipFile zip) throws IOException
	{
		Enumeration<? extends ZipEntry> modsDirIter = zip.entries();
		File outputDir = new File(actModsDir.toPath() + zip.getName().replace(".zip", "").replace(".jar", "") + File.separator);
		
		while (modsDirIter.hasMoreElements())
		{
			ZipEntry currClass = modsDirIter.nextElement();
			InputStream zipInputStream = zip.getInputStream(currClass);
			OutputStream output = new FileOutputStream(outputDir);
			
			//Probably the only instance in the entirety of N-API that an external library is used that isn't necessary for
			//key mod functionality.
			IOUtils.copy(zipInputStream, output);
		}
		
		System.gc();
		return outputDir;
	}
	
	/**
	 * Loads the classes in the directory.
	 * @param dir
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchFieldException
	 * @throws InstantiationException 
	 */
	public static final void loadClasses(File dir) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, InstantiationException
	{
		if (dir.isDirectory())
		{
			for (File currFile : dir.listFiles())
			{
				String binaryName = getModBinaryName(dir);
				
				if (binaryName.contains("mod_"))
				{
					NAPILogHelper.logWarn("The class with the binary name of " + binaryName + " has horrible naming practice. They should rename their class immediately!");
				}
				
				IModRegister binNameInstance = (IModRegister) Class.forName(binaryName).newInstance();
				
				processAnnotations(new Mod(binNameInstance));
			}
		}
		else
		{
			throw new IllegalArgumentException("The dir passed must be a directory to allow for the files to be loaded!");
		}
		
		System.gc();
	}
	
	/**
	 * Processes annotations, doing special things depending on annotations.
	 * @param binaryName
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static final void processAnnotations(Mod mod) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		IModRegister modRegister = mod.getMainClass();
		Annotation[] registerAnnotations = mod.getClassAnnotations();
		Map<Method, Annotation[]> registerMethods = mod.getMethodAnnotations();
		
		Iterator<Method> methodIterator = registerMethods.keySet().iterator();
		Iterator annotationIterator = registerMethods.entrySet().iterator();
		
		while (methodIterator.hasNext())
		{
			AnnotationHandlerRegistry.callAllMethodHandlers((Annotation[]) annotationIterator.next(), methodIterator.next(), modRegister);
		}
		
		modIds.put(modRegister.getModId(), mod);

		if (registerAnnotations != null)
		{
			//Makes it easier to add future annotations.
			for (Annotation annotation : registerAnnotations)
			{
				//Gets all annotation handlers to handle the current annotation.
				for (IAnnotationHandler currHandler : AnnotationHandlerRegistry.getAnnotationHandlers())
				{
					currHandler.handleAnnotation(annotation, modRegister);
				}
			}
		}
		else
		{
			loadMod(modRegister);
		}
		
		System.gc();
	}
	
	public static final void loadMod(IModRegister mod)
	{
		mods.add(new Mod(mod.getModId(), mod.getVersion(), mod));
	}
	
	/**
	 * Gets the mod id from the file ModID.modid in a file.
	 * @param dirToCheck
	 * @return
	 * @throws FileNotFoundException
	 */
	public static final String getModBinaryName(File dirToCheck)
	{
		try
		{
			String binaryName = "";

			for (File currFile : dirToCheck.listFiles())
			{
				//If the file is called ModID.modid, get the first line and assign binaryName to that value.
				if (currFile.getName().contains("ModID.modid"))
				{
					Scanner scanner = new Scanner(currFile);
					binaryName = scanner.nextLine();
					scanner.close();
				}
			}

			System.gc();

			if (binaryName != "")
			{
				return binaryName;
			}
			else
			{
				throw new IllegalArgumentException("There is no ModID.modid file in the directory " + dirToCheck.getPath());
			}
		}
		catch (FileNotFoundException e)
		{
			NAPILogHelper.logError(e);
			return "";
		}
	}
	
	/**
	 * This checks dependencies and libraries before calling a mod's modPreInit method.
	 * @throws OutdatedLibraryException 
	 * @throws ModDependencyNotFoundException 
	 */
	public static final void callAllPreInits()
	{
		try
		{	
			//Handles the annotated methods, a bit of reflection magic.
			Iterator methodsIterator = preInitMethods.entrySet().iterator();
			Iterator<IModRegister> objsIterator = preInitMethods.keySet().iterator();
			
			while (methodsIterator.hasNext())
			{
				((Method) methodsIterator.next()).invoke(objsIterator.next(), new Object[] {});
			}
			
			//What iterates the classes in mods.
			Iterator<Mod> modsIterator = mods.iterator();

			while (modsIterator.hasNext())
			{
				IModRegister currRegister = modsIterator.next().getMainClass();
				currRegister.addRequiredMods();
				currRegister.addRequiredLibraries();

				Iterator<String> dependenciesIterator = currRegister.dependencies.iterator();
				Iterator libraryIterator = currRegister.libraryDependencies.entrySet().iterator();
				Iterator<String> libraryVersionIterator = currRegister.libraryDependencies.keySet().iterator();

				//Iterates the dependencies of the mod
				while (dependenciesIterator.hasNext())
				{
					//An independent dependency.
					IModRegister currDependency = null;

					try
					{
						currDependency = (IModRegister) (Class.forName((String) dependenciesIterator.next())).newInstance();
					}
					catch (NullPointerException | ClassNotFoundException | InstantiationException | IllegalAccessException e)
					{
						//If there are no dependencies, stop this run of the dependency system. The only time NullPointerExceptions are actually useful -_-.
						if (e instanceof NullPointerException)
						{
							break;
						}
						else if (e instanceof InstantiationException)
						{
							//Tell the user (angrily) to tell the author that they should not be making their register an interface.
							NAPILogHelper.logError("Please ask the author of the mod " + currRegister.getModId() + " WHAT THE HELL ARE YOU DOING MAKING YOUR MOD DEPENDANT ON AN INTERFACE?!");
						}
					}

					if (!mods.contains(currDependency))
					{
						throw new ModDependencyNotFoundException(currDependency.getModId());
					}

					if (libraryVersionIterator != null && libraryIterator != null)
					{
						//The current library version.
						String currLibraryVersion = libraryVersionIterator.next();
						//The current library
						IModRegister currLib = (IModRegister) libraryIterator.next();

						if (currLibraryVersion == "")
						{
							//Tell the user that a library doesn't have a version.
							throw new RuntimeException("The library " + currLib.getModId() + " does NOT have a version! Contact the library's creator to fix this and then ask them why they aren't adding a version to their mod, especially if it's a library -_-!");
						}

						//The version of the requested minimum library version.
						int[] currVersion = ParseUtils.parseVersionNumber(currLibraryVersion);
						//The actual library version.
						int[] currLibVersion = ParseUtils.parseVersionNumber(currLib.getVersion());

						for (int currNumber : currVersion)
						{
							for (int i = 0; i == currLibVersion.length; i++)
							{
								if (currLibVersion[i] >= currNumber)
								{
									//One of the versions are ok, check the next one.
									continue;
								}
								else
								{
									//Let the user know that a library is outdated.
									throw new OutdatedLibraryException(currLib.getModId());
								}
							}
						}

						currLib.preModInit();
					}
				}

				//Gets the register to do it's preModInit stuff.
				currRegister.preModInit();
				ResourcesRegistry.addAllResourceDomains();
			}
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ModDependencyNotFoundException | OutdatedLibraryException e1)
		{
			NAPILogHelper.logError(e1);
		}
		
		System.gc();
	}
	
	/**
	 * Calls all register's modInit method.
	 */
	public static final void callAllInits()
	{
		try
		{
			Iterator methodsIterator = initMethods.entrySet().iterator();
			Iterator<IModRegister> objsIterator = initMethods.keySet().iterator();
			
			while (methodsIterator.hasNext())
			{
				((Method) methodsIterator.next()).invoke(objsIterator.next(), new Object[] {});
			}
			
			Iterator<Mod> modsIterator = mods.iterator();

			while (modsIterator.hasNext())
			{
				IModRegister currRegister = modsIterator.next().getMainClass();

				currRegister.modInit();
			}
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1)
		{
			NAPILogHelper.logError(e1);
		}
		
		System.gc();
	}
	
	/**
	 * Calls all register's postModInit methods.
	 */
	public static final void callAllPostInits()
	{
		try
		{
			Iterator methodsIterator = postInitMethods.entrySet().iterator();
			Iterator<IModRegister> objsIterator = postInitMethods.keySet().iterator();
			
			while (methodsIterator.hasNext())
			{
				((Method) methodsIterator.next()).invoke(objsIterator.next(), new Object[] {});
			}
			
			Iterator<Mod> modsIterator = mods.iterator();

			while (modsIterator.hasNext())
			{
				IModRegister currRegister = modsIterator.next().getMainClass();

				currRegister.postModInit();
				RenderRegistry.addAllRenders();
			}
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1)
		{
			NAPILogHelper.logError(e1);
		}
		
		//Does the work of adding the potions to the Potion.potionTypes array. You know, just in case.
		PotionRegistry.addAllPotions();
		
		System.gc();
	}
	
	/**
	 * Loads an ASM class.
	 * @param theClass
	 * @param bytes
	 */
	@TestFeature(firstAppearance = "1.0")
	public static final void loadASMClass(Class theClass, byte[] bytes)
	{
		NModLoader.defineClass(theClass.getName(), bytes);
	}
}
