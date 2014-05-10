package co.uk.niadel.api.modhandler.loadhandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
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
import co.uk.niadel.api.annotations.AnnotationHandlerRegistry;
import co.uk.niadel.api.annotations.IAnnotationHandler;
import co.uk.niadel.api.annotations.MPIAnnotations.Library;
import co.uk.niadel.api.annotations.MPIAnnotations.UnstableLibrary;
import co.uk.niadel.api.annotations.MPIAnnotations.UnstableMod;
import co.uk.niadel.api.annotations.VersionMarkingAnnotations.TestFeature;
import co.uk.niadel.api.asm.ASMRegistry;
import co.uk.niadel.api.crafting.RecipesRegistry;
import co.uk.niadel.api.events.EventsList;
import co.uk.niadel.api.events.apievents.EventLoadMod;
import co.uk.niadel.api.modhandler.IModRegister;
import co.uk.niadel.api.rendermanager.RenderRegistry;
import co.uk.niadel.api.util.UtilityMethods;

@TestFeature(stable = false, firstAppearance = "1.0")
/**
 * This isn't actually as flexible as the Forge mod loader, but it does most of the
 * same stuff. Flexibility may be improved in a later version of N-API.
 * 
 * The complete rewrite of the old NModLoader (Which I left for historical reasons as
 * NModLoaderOld). It copies some of the old methods, though. I'm surprised at how
 * little code it took this time around. I guess it's quite easy the 100th time.
 * 
 * TODO Remove the need of the ModID.modid file.
 * 
 * This is very System.gc() intensive to keep resources used at a minimum.
 * 
 * TO JAVA GARBAGE COLLECTOR:
 * I highly recommend you run after every method TBH :P
 * @author Niadel
 *
 */
public class NModLoader 
{
	public static Minecraft theMinecraft = Minecraft.getMinecraft();
	public static Profiler mcProfiler = Minecraft.mcProfiler;
	public static Map<String, String> mods = new HashMap<>();
	
	/**
	 * A list of library classes, or classes marked with the @Library annotation.
	 */
	public static List<IModRegister> modLibraries = new ArrayList();
	
	/**
	 * The main Minecraft directory.
	 */
	public static File mcMainDir = theMinecraft.mcDataDir;
	
	/**
	 * The directory for mods to be put in, the same folder Forge uses.
	 */
	public static File mcModsDir = new File(theMinecraft.mcDataDir + File.separator + "mods" + File.separator);
	
	/**
	 * Where the decompressed class files are copied to.
	 */
	public static File actModsDir = new File(mcModsDir + "act_mods" + File.separator);

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
	public static final void loadModsFromDir() throws ZipException, IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, InstantiationException
	{
		System.out.println(mcMainDir.toPath().toString());
		initNAPIRegister((Class<? extends IModRegister>) Class.forName("co.uk.niadel.api.modhandler.n_api.ModRegister"));
		
		if (mcModsDir.listFiles() != null)
		{
			for (File currFile : mcModsDir.listFiles())
			{
				File nextLoad = extractFromZip(new ZipFile(currFile));
				loadClasses(nextLoad);
			}
			
			//TODO Make it so these actually are called at their respective points of initialisation.
			callAllPreInits();
			ASMRegistry.invokeAllTransformers();
			callAllInits();
			callAllPostInits();
		}
		
		System.gc();
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
			register.preModInit();
			register.modInit();
			register.postModInit();
			modLibraries.add(register);
		}
		catch (SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException e)
		{
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
		File actModsDirectory = new File(mcModsDir + "act_mods" + File.separator);
		Enumeration<? extends ZipEntry> modsDirIter = zip.entries();
		actModsDirectory.mkdirs();
		File outputDir = new File(actModsDirectory + zip.getName().replace(".zip", "").replace(".jar", "") + File.separator);
		
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
				String binaryName = getModId(dir);
				
				Class<? extends IModRegister> modRegisterClass = (Class<? extends IModRegister>) Class.forName(binaryName);
				IModRegister modRegister = modRegisterClass.newInstance();
				EventsList.fireEvent(new EventLoadMod(binaryName), "EventLoadMod");
				Annotation[] registerAnnotations = modRegisterClass.getAnnotations();
				
				if (registerAnnotations != null)
				{
					//Makes it easier to add future annotations.
					for (Annotation annotation : registerAnnotations)
					{
						//If the class has the @Library annotation, add it to the modLibraries list.
						if (annotation.annotationType() == Library.class)
						{
							//Add it to the libraries list instead of the mods list.
							modLibraries.add(modRegister);
						}
						else if (annotation.annotationType() == UnstableMod.class)
						{
							//Tell the user that the mod is unstable and could break stuff drastically
							System.out.println("[IMPORTANT] " + ((UnstableMod) annotation).specialMessage());
							//Put it in the regular mods thing.
							mods.put(modRegister.toString().replace("class ", ""), modRegister.toString().replace("class ", ""));
						}
						else if (annotation.annotationType() == UnstableLibrary.class)
						{
							//Tell the user that the library is unstable and mods using it could break
							System.out.println("[IMPORTANT] " + ((UnstableLibrary) annotation).specialMessage());
							modLibraries.add(modRegister);
						}
						
						//Gets all annotation handlers to handle the current annotation.
						for (IAnnotationHandler currHandler : AnnotationHandlerRegistry.getAnnotationHandlers())
						{
							currHandler.handleAnnotation(annotation, modRegister);
						}
					}
				}
				else
				{
					mods.put(modRegister.toString().replace("class ", ""), modRegister.toString().replace("class ", ""));
				}
			}
		}
		else
		{
			throw new IllegalArgumentException("The dir passed must be a directory to allow for the files to be loaded!");
		}
		
		System.gc();
	}
	
	/**
	 * Gets the mod id from the file ModID.modid in a file.
	 * @param dirToCheck
	 * @return
	 * @throws FileNotFoundException
	 */
	public static final String getModId(File dirToCheck) throws FileNotFoundException
	{
		String binaryName = null;
		
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
		return binaryName;
	}
	
	/**
	 * By far the most complicated method, this checks dependencies and libraries before
	 * calling a mod's modPreInit method.
	 */
	public static final void callAllPreInits()
	{
		//What iterates the classes in mods.
		Iterator<String> modsIterator = mods.keySet().iterator();
		//Iterates the IModRegister objects. Lawd darnit, why don't generics work here?!
		Iterator modsObjectIterator = mods.entrySet().iterator();
		
		while (modsIterator.hasNext())
		{
			//The current class being iterated.
			String currClass = (String) modsIterator.next();
			
			//The current IModRegister object.
			IModRegister currRegister = (IModRegister) modsObjectIterator.next();
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
						throw new RuntimeException("Please tell the author of the mod " + currRegister.modId + " WHAT THE HELL ARE YOU DOING MAKING YOUR MOD DEPENDANT ON AN INTERFACE?!");
					}
				}
					
				if (!mods.containsValue(currDependency))
				{
					//Signify to the user that a mod dependency is not found.
					throw new RuntimeException("A mod dependency has not been found!");
				}
				
				if (libraryVersionIterator != null && libraryIterator != null)
				{
					//The current library version.
					String currLibraryVersion = (String) libraryVersionIterator.next();
					//The current library
					IModRegister currLib = (IModRegister) libraryIterator.next();
					
					if (currLibraryVersion == "")
					{
						//Tell the user that a library doesn't have a version.
						throw new RuntimeException("The library " + currLib.modId + " does NOT have a version! Contact the library's creator to fix this!");
					}
					
					//The version of the requested minimum library version.
					int[] currVersion = UtilityMethods.parseVersionNumber(currLibraryVersion);
					//The actual library version.
					int[] currLibVersion = UtilityMethods.parseVersionNumber(currLib.version);
					
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
								throw new RuntimeException("An installed library is outdated! Please update mod library " + currLib.modId + "!");
							}
						}
					}
				}
			}
			
			//Gets the register to do it's preModInit stuff.
			currRegister.preModInit();
		}
		
		System.gc();
	}
	
	public static final void callAllInits()
	{
		Iterator<String> modsIterator = mods.keySet().iterator();
		Iterator modRegisterIterator = mods.entrySet().iterator();
		
		while (modsIterator.hasNext())
		{
			try
			{
				Class<? extends IModRegister> currClass = (Class<? extends IModRegister>) Class.forName(modsIterator.next());
			}
			catch (ClassNotFoundException | NullPointerException e)
			{
				if (e instanceof NullPointerException)
				{
					break;
				}
			}
			IModRegister currRegister = (IModRegister) modRegisterIterator.next();
			
			currRegister.modInit();
			//ASM classes are registered in modPreInit or modInit, call them here
		}
		
		System.gc();
	}
	
	public static final void callAllPostInits()
	{
		Iterator<String> modsIterator = mods.keySet().iterator();
		Iterator modRegisterIterator = mods.entrySet().iterator();
		
		while (modsIterator.hasNext())
		{
			try
			{
				Class<? extends IModRegister> currClass = (Class<? extends IModRegister>) Class.forName(modsIterator.next());
			}
			catch (ClassNotFoundException | NullPointerException e)
			{
				if (e instanceof NullPointerException)
				{
					break;
				}
				e.printStackTrace();
			}
			IModRegister currRegister = (IModRegister) modRegisterIterator.next();
			
			currRegister.postModInit();
			RecipesRegistry.addAllRecipes();
			RenderRegistry.addAllRenders();
		}
		
		System.gc();
	}
	
	
	
	
	
	
	
	
	
	
}
