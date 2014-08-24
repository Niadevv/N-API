package co.uk.niadel.mpi.modhandler.loadhandler;

import co.uk.niadel.mpi.asm.ASMRegistry;
import co.uk.niadel.mpi.client.ClientRegistry;
import co.uk.niadel.mpi.common.NAPIData;
import co.uk.niadel.mpi.init.Launch;
import co.uk.niadel.mpi.modhandler.*;
import co.uk.niadel.mpi.modhandler.ModRegister;
import co.uk.niadel.mpi.util.ModList;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.jar.JarFile;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import co.uk.niadel.mpi.annotations.AnnotationHandlerRegistry;
import co.uk.niadel.mpi.annotations.IAnnotationHandler;
import co.uk.niadel.mpi.annotations.VersionMarkingAnnotations.TestFeature;
import co.uk.niadel.mpi.annotations.MPIAnnotations.*;
import co.uk.niadel.mpi.client.resources.ResourcesRegistry;
import co.uk.niadel.mpi.potions.PotionRegistry;
import co.uk.niadel.mpi.util.NAPILogHelper;
import co.uk.niadel.mpi.util.MCData;

/**
 * This isn't actually as flexible as FML, but it does most of the same stuff. Flexibility may be improved in a later version of N-API.
 * It is worth noting that this is actually a LOT simpler than FML's loader, which spans a LOT of files.
 * 
 * TODO Remove the need of the ModID.modid file.
 * 
 * @author Niadel
 *
 */
@TestFeature(stable = false, firstAppearance = "1.0")
public class NModLoader extends URLClassLoader
{
	/**
	 * The NModLoader INSTANCE.
	 */
	public static final @Internal NModLoader INSTANCE = new NModLoader(new URL[0], getSystemClassLoader());

	/**
	 * The Minecraft object.
	 */
	public static final Minecraft theMinecraft = Minecraft.getMinecraft();

	/**
	 * List of modids that have been found and are scheduled to be loaded/have been loaded.
	 */
	public static final ModList mods = new ModList();
	
	/**
	 * A list of modids belonging to Forge mods, added so N-API mods can test for Forge mods.
	 */
	public static final List<String> forgeModids = new ArrayList<>();

	/**
	 * The main Minecraft directory.
	 */
	public static final File mcMainDir = new File(theMinecraft.mcDataDir.getAbsolutePath().substring(0, theMinecraft.mcDataDir.getAbsolutePath().length() - 1));
	
	/**
	 * The directory for mods to be put in, the same folder Forge uses for convenience.
	 */
	public static final File mcModsDir = new File(theMinecraft.mcDataDir + "/mods/");

	/**
	 * Methods to execute on preInit.
	 */
	public static final Map<IModRegister, Method> preInitMethods = new HashMap<>();
	
	/**
	 * Methods to execute on init.
	 */
	public static final Map<IModRegister, Method> initMethods = new HashMap<>();
	
	/**
	 * Methods to execute on postInit.
	 */
	public static final Map<IModRegister, Method> postInitMethods = new HashMap<>();

	/**
	 * Whether or not N-API should initialise - False if the user's Java version is not 7.
	 */
	public static boolean shouldInit = true;

	public NModLoader(URL[] urls, ClassLoader parent)
	{
		super(urls, parent);
	}

	/**
	 * Looks for whether or not the mod with the specified modId exists.
	 * @param modId The modId to check for.
	 * @return Whether the mod exists.
	 */
	public static final boolean doesModExist(String modId)
	{
		return mods.contains(mods.getModContainerById(modId));
	}
	
	/**
	 * Looks for whether or not the library with the specified modId exists.
	 * @param modId The modId to search for.
	 * @return Whether the library exists.
	 */
	public static final boolean doesLibraryExist(String modId)
	{
		return mods.doesListContainLibrary(modId);
	}

	public final Package[] getPackages()
	{
		return super.getPackages();
	}

	public static final void defineClass(String className, byte[] bytes)
	{
		INSTANCE.defineClass(className, bytes, 0, bytes.length);
	}
	
	/**
	 * Adds a URL into the class path.
	 * @param url The url to add.
	 */
	@Internal
	private void addUrl(URL url)
	{
		super.addURL(url);
	}
	
	/**
	 * Loads a URL into the class path.
	 * @param url The url to add.
	 */
	public static final void loadUrl(URL url)
	{
		INSTANCE.addURL(url);
	}
	
	/**
	 * Gets a IModRegister object by it's mod id.
	 * @param modId The modId to get the Mod object of.
	 * @return The Mod with the id of modId.
	 */
	public static final IModRegister getModByModId(String modId)
	{
		return mods.getModById(modId);
	}
	
	/**
	 * Gets the mod container corresponding to the specified mod id.
	 * @param modId The modId to get the mod register of.
	 * @return The mod container with the id of modId.
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
		if (Launch.checkJavaVersion())
		{
			try
			{
				if (!mcModsDir.exists())
				{
					mcModsDir.mkdir();
					NAPILogHelper.log("Created mods folder at " + mcModsDir.toPath().toString() + "!");
				}

				loadUrl(mcModsDir.toURI().toURL());

				initNAPIRegister((Class<? extends IAdvancedModRegister>) Class.forName(MCData.getNAPIRegisterClass()));

				if (mcModsDir.listFiles() != null)
				{
					for (File currFile : mcModsDir.listFiles())
					{
						if (!currFile.isDirectory())
						{
							JarFile nextLoad = loadModAsJF(currFile);

							if (nextLoad != null)
							{
								//Just in case.
								loadUrl(currFile.toURI().toURL());
								loadClasses(nextLoad);
							}
						}
					}

					registerTransformers();
				}
			} catch (IOException | ClassNotFoundException | SecurityException | IllegalArgumentException e)
			{
				e.printStackTrace();
				NAPILogHelper.logError(e);
			}
		}
		else
		{
			shouldInit = false;
		}
	}
	
	/**
	 * Calls all mod register's registerTransformers() method.
	 */
	public static final void registerTransformers()
	{
		Iterator<IModContainer> modsObjectIterator = mods.iterator();
		Iterator<Library> modsLibraryIterator = mods.getLibraryContainers().iterator();
		
		while (modsObjectIterator.hasNext())
		{
			IModContainer nextContainer = modsObjectIterator.next();

			if (!nextContainer.isLibrary())
			{
				Mod nextMod = (Mod) nextContainer;

				if (nextMod.isAdvancedRegister())
				{
					nextMod.registerTransformers();
				}
			}
		}
		
		NAPILogHelper.log("Finished registering mod ASM transformers! Now loading library ASM transformers!");
		
		while (modsLibraryIterator.hasNext())
		{
			//Generics for the win!
			Library nextLib = modsLibraryIterator.next();

			if (nextLib.isAdvancedRegister())
			{
				nextLib.registerTransformers();
			}
		}
		
		NAPILogHelper.log("Finished loading all ASM transformers!");
	}
	
	/**
	 * Only ever used to load the N-API ModRegister, so the regular checks are ignored.
	 * @param theClass The class that is loaded.
	 */
	private static final void initNAPIRegister(Class<? extends IAdvancedModRegister> theClass)
	{
		try
		{
			if (theClass == ModRegister.class)
			{
				IAdvancedModRegister register = theClass.newInstance();

				if (register.getModId() == NAPIData.MODID)
				{
					register.registerEventHandlers();
					register.registerTransformers();
					processAnnotations(new Mod(register.getModId(), register.getVersion(), register));
					register.preModInit();
					register.modInit();
					register.postModInit();
				}
			}
		}
		catch (SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException e)
		{
            NAPILogHelper.logCritical("ERROR WHILST LOADING N-API MOD REGISTER!");
			//Crash the game - Failure to load the N-API register can break a LOT of stuff.
			CrashReport crashReport = CrashReport.makeCrashReport(e, "Loading N-API ModRegister");
			crashReport.makeCategory("Initialising N-API");
			throw new ReportedException(crashReport);
		}
	}

	/**
	 * Gets the jar of the specified file after checking it.
	 * @param file The file to load.
	 * @return The jar file requested, or null if checks are not met.
	 */
	public static final JarFile loadModAsJF(File file)
	{
		if (file.getPath().endsWith(".jar") && !file.isDirectory())
		{
			try
			{
				return new JarFile(file);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return null;
	}
	
	/**
	 * Loads the classes in the directory.
	 * @param dir The directory to load the classes of.
	 */
	public static final void loadClasses(JarFile dir)
	{
		try
		{
			String binaryName = getModBinaryName(dir);

			if (binaryName != null)
			{
				if (binaryName.contains("mod_"))
				{
					NAPILogHelper.logWarn("The class with the binary name of " + binaryName + " has horrible naming practice. They should rename their class immediately!");
				}

				IModRegister binNameInstance = (IModRegister) Class.forName(binaryName).newInstance();

				processAnnotations(new Mod(binNameInstance).setFileLocation(new File(mcModsDir.toPath() + dir.getName())));
			}
		}
		catch (InstantiationException | ClassNotFoundException | IllegalAccessException e)
		{
			NAPILogHelper.logError("Unable to load the classes for jar file " + dir.getName() + "!");
			e.printStackTrace();

			if (e instanceof ClassNotFoundException)
			{
				NAPILogHelper.logError("It would appear that the class specified by the jar file " + dir.getName() + "'s ModID.modid file is incorrect!");
			}
			else
			{
				NAPILogHelper.logError("It would appear that the jar file " + dir.getName() + "'s main mod class has a constructor with a parameter or is protected or private! DO NOT DO THIS!");
			}
		}
	}
	
	/**
	 * Processes annotations, doing special things depending on annotations.
	 * @param mod The mod to process the annotations of.
	 */
	public static final void processAnnotations(Mod mod)
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
			//Makes it easier to add annotations.
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
	 * @param mod The mod to load.
	 */
	public static final void loadMod(IModRegister mod)
	{
		mods.addMod(new Mod(mod.getModId(), mod.getVersion(), mod));
		NAPILogHelper.log("Loaded mod " + mod.getModId() + "!");
	}
	
	/**
	 * Adds the mod to mods.
	 * @param mod The mod to load the library of.
	 */
	public static final void loadLibrary(IModRegister mod)
	{
		mods.addMod(mod, true);
		NAPILogHelper.log("Loaded library " + mod.getModId() + "!");
	}
	
	/**
	 * Gets the mod id from the file ModID.modid in a file.
	 * @param modJarFile The jar file of the mod to get the main class binary name of.
	 * @return The mod's main class binary name.
	 */
	public static final String getModBinaryName(JarFile modJarFile)
	{
		try
		{
			Scanner scanner = new Scanner(modJarFile.getInputStream(modJarFile.getEntry("ModID.modid")));
			return scanner.nextLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * This invokes all of the ASM transformers, checks dependencies and libraries before calling a mod's modPreInit method.
	 */
	public static final void callAllPreInits()
	{
		if (shouldInit)
		{
			ASMRegistry.invokeAllTransformers();

			Iterator<Mod> modsIterator = mods.getModContainers().iterator();

			while (modsIterator.hasNext())
			{
				IModContainer currContainer = modsIterator.next();

				if (!currContainer.isLibrary())
				{
					Mod currMod = (Mod) currContainer;

					if (currMod.isAdvancedRegister())
					{
						IAdvancedModRegister currRegister = (IAdvancedModRegister) currMod.getMainClass();

						if (checkDependencies(currRegister))
						{
							currRegister.preModInit();
						}
					}
				}
			}

			Iterator<Library> libraryIterator = mods.getLibraryContainers().iterator();

			while (modsIterator.hasNext())
			{
				Library currLibContainer = libraryIterator.next();

				if (currLibContainer.isAdvancedRegister())
				{
					IAdvancedModRegister currLibrary = (IAdvancedModRegister) currLibContainer.getMainClass();

					if (checkDependencies(currLibrary))
					{
						currLibrary.preModInit();
					}
					else
					{
						NAPILogHelper.logError("The library " + currLibrary.getModId() + "'s dependency requirements were NOT met! Skipping it's loading!");
						//Remove the library so it isn't put through the other phases of loading.
						mods.getMods().remove(currLibrary);
					}
				}
			}

			Iterator<Entry<IModRegister, Method>> methodsIterator = preInitMethods.entrySet().iterator();

			while (methodsIterator.hasNext())
			{
				Entry<IModRegister, Method> currMethod = methodsIterator.next();

				try
				{
					currMethod.getValue().invoke(currMethod.getKey(), new Object[]{});
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
				{
					NAPILogHelper.logError("There was an error invoking " + currMethod.getValue().getName() + "! If it required parameters, please remove them!");
					e.printStackTrace();
				}
			}

			ResourcesRegistry.addAllResourceDomains();
			NAPILogHelper.log("Called all mod's modPreInit methods!");
		}
	}
	
	/**
	 * Checks a register's dependencies. Returns true if checks were all successful.
	 * @param register The register to check the dependencies of.
	 * @return Whether or not the register passed the checks.
	 */
	public static final boolean checkDependencies(IAdvancedModRegister register)
	{
		return DependenciesRegistry.checkDependencies(register);
	}
	
	/**
	 * Calls all register's modInit method.
	 */
	public static final void callAllInits()
	{
		if (shouldInit)
		{
			try
			{
				Iterator methodsIterator = initMethods.entrySet().iterator();
				Iterator<IModRegister> objsIterator = initMethods.keySet().iterator();

				while (methodsIterator.hasNext())
				{
					((Method) methodsIterator.next()).invoke(objsIterator.next(), new Object[]{});
				}

				Iterator<IModContainer> modsIterator = mods.iterator();

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
	}
	
	/**
	 * Calls all register's postModInit methods.
	 */
	public static final void callAllPostInits()
	{
		if (shouldInit)
		{
			try
			{
				Iterator methodsIterator = postInitMethods.entrySet().iterator();
				Iterator<IModRegister> objsIterator = postInitMethods.keySet().iterator();

				while (methodsIterator.hasNext())
				{
					((Method) methodsIterator.next()).invoke(objsIterator.next(), new Object[]{});
				}

				Iterator<IModContainer> modsIterator = mods.iterator();

				while (modsIterator.hasNext())
				{
					IModRegister currRegister = modsIterator.next().getMainClass();

					currRegister.postModInit();
					ClientRegistry.addAllEntityRenders();
				}

				NAPILogHelper.log("Finished calling all mod's register methods!");
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1)
			{
				NAPILogHelper.logError(e1);
			}

			//Does the work of adding the potions to the Potion.potionTypes array. You know, just in case.
			PotionRegistry.addAllPotions();
		}
	}
	
	/**
	 * Loads an ASM class.
	 * @param theClass The class that was transformed to load.
	 * @param bytes The bytes of theClass.
	 */
	@TestFeature(firstAppearance = "1.0")
	public static final void loadASMClass(Class theClass, byte[] bytes)
	{
		NModLoader.defineClass(theClass.getName(), bytes);
		NAPILogHelper.log("Loaded ASM class " + theClass.getName());
	}
}
