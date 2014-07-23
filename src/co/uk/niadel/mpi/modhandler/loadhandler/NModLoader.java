package co.uk.niadel.mpi.modhandler.loadhandler;

import co.uk.niadel.mpi.util.ModList;
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
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.zip.ZipEntry;
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
import co.uk.niadel.mpi.modhandler.IModRegister;
import co.uk.niadel.mpi.potions.PotionRegistry;
import co.uk.niadel.mpi.rendermanager.RenderRegistry;
import co.uk.niadel.mpi.util.NAPILogHelper;
import co.uk.niadel.mpi.util.MCData;

/**
 * This isn't actually as flexible as FML, but it does most of the
 * same stuff. Flexibility may be improved in a later version of N-API.
 * 
 * TODO Remove the need of the ModID.modid file.
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
	 * List of modids that have been found and are scheduled to be loaded/have been loaded.
	 */
	public static ModList mods = new ModList();
	
	/**
	 * A list of modids belonging to Forge mods, added so N-API mods can test for Forge mods.
	 */
	public static List<String> forgeModids = new ArrayList<>();

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
	public static File actModsDir = new File(theMinecraft.mcDataDir + File.separator + "act_mods" + File.separator);

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
		if (mods.contains(mods.getModContainerById(modId)))
		{
			return true;
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
		return mods.doesListContainLibrary(modId);
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
	@Internal
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
	 * Gets a IModRegister object by it's mod id.
	 * @param modId
	 * @return
	 */
	public static final IModRegister getModByModId(String modId)
	{
		return mods.getModById(modId);
	}
	
	/**
	 * Gets the mod container corresponding to the specified mod id.
	 * @param modId
	 * @return
	 */
	public static final IModContainer getModContainerByModId(String modId)
	{
		return mods.getModContainerById(modId);
	}
	
	/**
	 * The entry point for the loader.
	 */
	public static final void loadModsFromDir()
	{
		try
		{
			if (!mcModsDir.exists())
			{
				mcModsDir.mkdir();
				NAPILogHelper.log("Created mods folder at " + mcModsDir.toPath().toString() + "!");
			}
			
			if (!actModsDir.exists())
			{
				actModsDir.mkdirs();
				NAPILogHelper.logWarn("Please note it will take longer to load N-API with lots of mods as they need to be prepared for loading first! If you only have one mod, ignore this!");
			}
			
			loadUrl(actModsDir.toURI().toURL());
			
			initNAPIRegister((Class<? extends IModRegister>) Class.forName(MCData.getNAPIRegisterClass()));

			if (mcModsDir.listFiles() != null)
			{
				for (File currFile : mcModsDir.listFiles())
				{
					File nextLoad = extractFromZip(new ZipFile(currFile));
					//Just in case.
					loadUrl(nextLoad.toURI().toURL());
					loadClasses(nextLoad);
					registerTransformers();
				}
			}
		}
		catch (IOException | ClassNotFoundException | SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException e)
		{
			e.printStackTrace();
			NAPILogHelper.logError(e);
		}
	}
	
	/**
	 * Calls all mod register's registerTransformers() method.
	 */
	public static final void registerTransformers()
	{
		Iterator<Mod> modsObjectIterator = mods.iterator();
		Iterator<Library> modsLibraryIterator = mods.getLibraryContainers().iterator();
		
		while (modsObjectIterator.hasNext())
		{
			modsObjectIterator.next().getMainClass().registerTransformers();
		}
		
		NAPILogHelper.log("Finished registering mod ASM transformers! Now loading library ASM transformers!");
		
		while (modsLibraryIterator.hasNext())
		{
			//Generics for the win!
			modsLibraryIterator.next().getMainClass().registerTransformers();
		}
		
		NAPILogHelper.log("Finished loading all ASM transformers!");
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
			IModRegister register = (IModRegister) theClass.newInstance();
			register.registerTransformers();
			processAnnotations(new Mod(register.getModId(), register.getVersion(), register));
			register.preModInit();
			register.modInit();
			register.postModInit();
		}
		catch (SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException | ClassNotFoundException e)
		{
            NAPILogHelper.logCritical("ERROR WHILST LOADING N-API MOD REGISTER!");
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
			
			//Probably the only instance in the entirety of N-API that an external library is 
			//used that isn't necessary for
			//key mod functionality.
			IOUtils.copy(zipInputStream, output);
		}
		
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
	public static final void loadClasses(File dir) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException
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
				
				processAnnotations(new Mod(binNameInstance).setFileLocation(dir));
			}
		}
		else
		{
			throw new IllegalArgumentException("The dir passed must be a directory to allow for the files to be loaded!");
		}
	}
	
	/**
	 * Processes annotations, doing special things depending on annotations.
	 * @param mod
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
		Iterator<Entry<Method, Annotation[]>> annotationIterator = registerMethods.entrySet().iterator();
		
		while (methodIterator.hasNext())
		{
			AnnotationHandlerRegistry.callAllMethodHandlers(annotationIterator.next().getValue(), methodIterator.next(), modRegister);
		}

		if (registerAnnotations != null)
		{
			//Makes it easier to add future annotations.
			for (IAnnotationHandler currHandler : AnnotationHandlerRegistry.getAnnotationHandlers())
			{
				for (Annotation annotation : registerAnnotations)
				{
					currHandler.handleAnnotation(annotation, modRegister);
				}

				//This check is because AnnotationHandlerNAPI has to manually load the class in a different
				//way due to the fact the annotation handler has to put load libraries as well as mods.
				if (currHandler.getClass().getName() != AnnotationHandlerNAPI.class.getName())
				{
					loadMod(mod.getMainClass());
				}
			}
		}
		
		NAPILogHelper.log("Finished processing annotations for the mod " + mod.modId + "!");
	}
	
	/**
	 * Converts an IModRegister into a Mod object and puts that Mod object into mods.
	 * @param mod
	 */
	public static final void loadMod(IModRegister mod)
	{
		mods.addMod(new Mod(mod.getModId(), mod.getVersion(), mod));
		NAPILogHelper.log("Loaded mod " + mod.getModId() + "!");
	}
	
	/**
	 * Adds the mod to mods.
	 * @param mod
	 */
	public static final void loadLibrary(IModRegister mod)
	{
		mods.addMod(mod, true);
		NAPILogHelper.log("Loaded library " + mod.getModId() + "!");
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
	 */
	public static final void callAllPreInits()
	{
		Iterator<Mod> modsIterator = mods.iterator();
		
		while (modsIterator.hasNext())
		{
			IModRegister currRegister = modsIterator.next().getMainClass();
			
			if (checkDependencies(currRegister))
			{
				currRegister.preModInit();
			}
		}
		
		Iterator<Library> libraryIterator = mods.iterator();
		
		while (modsIterator.hasNext())
		{
			IModRegister currLibrary = libraryIterator.next().getMainClass();
			
			if (checkDependencies(currLibrary))
			{
				currLibrary.preModInit();
			}
			
		}
		
		Iterator<Entry<IModRegister, Method>> methodsIterator = preInitMethods.entrySet().iterator();
		
		while (methodsIterator.hasNext())
		{
			Entry<IModRegister, Method> currMethod = methodsIterator.next();
			
			try
			{
				currMethod.getValue().invoke(currMethod.getKey(), new Object[] {});
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				NAPILogHelper.logError("There was an error invoking " + currMethod.getValue().getName() + "! If it required parameters, please remove them!");
				e.printStackTrace();
			}
		}
		
		ResourcesRegistry.addAllResourceDomains();
		NAPILogHelper.log("Called all mod's modPreInit methods!");
	}
	
	/**
	 * Checks a register's dependencies. Returns true if checks were all successful.
	 * @param register
	 * @return
	 */
	public static final boolean checkDependencies(IModRegister register)
	{
		boolean depsGood = true;
		boolean libDepsGood = true;
		
		//DEPENDENCIES CHECKING START
		Iterator<IModRegister> depsIterator = register.dependencies.iterator();
		
		while (depsIterator.hasNext())
		{
			if (mods.contains(depsIterator.next()))
			{
				continue;
			}
			else
			{
				depsGood = false;
			}
		}
		//DEPENDENCIES CHECKING END
		
		//LIBRARY CHECKING START
		Iterator<IModRegister> libDepsIterator = register.libraryDependencies.keySet().iterator();
		Iterator<Entry<IModRegister, String>> libDepsVersionsIter = register.libraryDependencies.entrySet().iterator();
		
		while (libDepsIterator.hasNext())
		{
			if (mods.compareContainerVersions(mods.getContainerFromRegister(register), mods.getContainerFromRegister(libDepsIterator.next())))
			{
				
			}
		}
		
		if (depsGood && libDepsGood)
		{
			return true;
		}
		else
		{
			return false;
		}
		//LIBRARY CHECKING END
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
			
			NAPILogHelper.log("Called all mod's modInit methods!");
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1)
		{
			NAPILogHelper.logError(e1);
		}
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
				RenderRegistry.addAllEntityRenders();
			}
			
			NAPILogHelper.log("Finished calling all mod's register methods!");
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1)
		{
			NAPILogHelper.logError(e1);
		}
		
		//Does the work of adding the potions to the Potion.potionTypes array. You know, just in case.
		PotionRegistry.addAllPotions();
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
		NAPILogHelper.log("Loaded ASM class " + theClass.getName());
	}
}
