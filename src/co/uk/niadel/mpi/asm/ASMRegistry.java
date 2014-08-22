package co.uk.niadel.mpi.asm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.mpi.util.NAPILogHelper;

/**
 * Where your register stuff to do with ASM in N-API.
 */
public final class ASMRegistry 
{
	public static final List<String> allClasses = new ArrayList<>();

	/**
	 * The list of ASM transformers registered.
	 */
	public static final List<IASMTransformer> asmTransformers = new ArrayList<>();
	
	/**
	 * A list of fully qualified class names that cannot be transformed, at least via the
	 * N-API approved method.
	 */
	public static final List<String> excludedClasses = new ArrayList<>();
	
	/**
	 * Adds an ASM transformer to the registry.
	 * @param transformer The transformer to register.
	 */
	public static final void registerTransformer(IASMTransformer transformer)
	{
		asmTransformers.add(transformer);
	}
	
	/**
	 * Calls all register transformers' manipulateBytecodes method, getting the requested
	 * bytes specified by each transformer.
	 */
	@Internal
	public static final void invokeAllTransformers()
	{
		Iterator<IASMTransformer> asmIterator = asmTransformers.iterator();

		while (asmIterator.hasNext())
		{
			callASMTransformer(asmIterator.next());
		}
	}

	/**
	 * Adds a class to exclude from transforming. You can add important MPI classes to this.
	 * @param excludedName The name to exclude.
	 */
	public static final void addASMClassExclusion(String excludedName)
	{
		//net.minecraft classes should ALWAYS be allowed to be transformed. The only exception is the FMLRenderAccessLibrary.
		if (excludedName == "net.minecraft.src.FMLRenderAccessLibrary" || !excludedName.startsWith("net.minecraft."))
		{
			excludedClasses.add(excludedName);
		}
	}

	/**
	 * Gets whether or not the specified class is excluded from ASM transformers.
	 * @param name The name of the class to check, the fully qualified name.
	 * @return Whether or not the class is excluded.
	 */
	public static final boolean isClassExcluded(String name)
	{
		Iterator<String> exclusionIter = excludedClasses.iterator();

		while (exclusionIter.hasNext())
		{
			String currName = exclusionIter.next();

			if (name.startsWith(currName) || currName == name)
			{
				return true;
			}
		}

		return false;
	}

	public static final void callASMTransformer(IASMTransformer currTransformer)
	{
		//Kept in case the requestTransformedClasses route has to be taken.
		//String[] requestedClasses = currTransformer.requestTransformedClasses();
		//Map<String, byte[]> bytesMap = new HashMap<>();

		try
		{
			for (String currClassName : allClasses)
			{
				if (!(currTransformer.getClass().getName() == NAPIASMNecessityTransformer.class.getName()) && !(currTransformer.getClass().getName() == NAPIASMEventHandlerTransformer.class.getName()))
				{
					if (!ASMRegistry.isClassExcluded(currClassName))
					{
						byte[] transformedBytes = currTransformer.manipulateBytecodes(currClassName);

						if (transformedBytes != null)
						{
							NModLoader.loadASMClass(Class.forName(currClassName), transformedBytes);
						}
					}
				}
				else
				{
					byte[] transformedBytes = currTransformer.manipulateBytecodes(currClassName);

					if (transformedBytes != null)
					{
						NModLoader.loadASMClass(Class.forName(currClassName), transformedBytes);
					}
				}
			}
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

				/* Old code kept in case byte are needed again.
				for (String currClassName : requestedClasses)
				{
					if (!(currTransformer.getClass().getName() == NAPIASMNecessityTransformer.class.getName()) && !(currTransformer.getClass().getName() == NAPIASMUtilsTransformer.class.getName()))
					{
						/*Don't allow users to edit the N-API files themselves unless it's NAPIASMTransformer,
						 * if they do, they could break a LOT of stuff. If they want a feature, they can ask for it
						 * on either the post or the GitHub (or do a PR on the GitHub). Only I
						 * should edit N-API's files, really, mainly because I have some grasp of
						 * what I'm doing - Anyone else touching N-API will likely kill all mods using N-API.*
						if (!currClassName.startsWith("co.uk.niadel.mpi") && !isClassExcluded(currClassName))
						{
							bytesMap.put(currClassName, ByteManipulationUtils.toByteArray(Class.forName(currClassName)));
						}
					}
					else
					{
						bytesMap.put(currClassName, ByteManipulationUtils.toByteArray(Class.forName(currClassName)));
					}
				}

				Iterator<Entry<String, byte[]>> bytesIterator = bytesMap.entrySet().iterator();

				while (bytesIterator.hasNext())
				{
					Entry<String, byte[]> currBytes = bytesIterator.next();
					byte[] transformedBytes = currTransformer.manipulateBytecodes(currBytes.getKey(), currBytes.getValue());
					NModLoader.defineClass(currBytes.getKey(), transformedBytes);
				}*/
	}

	/**
	 * Gets all of the classes loaded and puts them in allClasses.
	 */
	@Internal
	private static final void getAllLoadedClassNames()
	{
		Package[] allPackages = NModLoader.INSTANCE.getPackages();

		for (Package currPackage : allPackages)
		{
			String packageName = currPackage.getName();

			Class[] classesForPackage = getClassesForPackage(packageName);

			for (Class clazz : classesForPackage)
			{
				allClasses.add(clazz.getName());
			}
		}
	}

	//Next two methods are borrowed code from http://www.dzone.com/snippets/get-all-classes-within-package
	/*
	Changes from original code:
		Fixing the formatting to my style
		Getting rid of the assert statements
		Getting rid of unecessary Types in the instantiated generic types.
		Adding a try-catch instead of doing what I USED to do and make the method's throws declaration include the error (the first one
		doesn't have this in order to check if there was an error getting the class names, one of the uses of exceptions)
	*/
	@Internal
	private static final Class[] getClassesForPackage(String packageName)
	{
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

			if (classLoader != null)
			{
				String path = packageName.replace('.', '/');
				Enumeration<URL> resources = classLoader.getResources(path);
				List<File> dirs = new ArrayList<>();

				while (resources.hasMoreElements())
				{
					URL resource = resources.nextElement();
					dirs.add(new File(resource.getFile()));
				}

				ArrayList<Class> classes = new ArrayList<>();

				for (File directory : dirs)
				{
					classes.addAll(findClasses(directory, packageName));
				}

				return classes.toArray(new Class[classes.size()]);
			}
			else
			{
				return null;
			}
		}
		catch (IOException e)
		{
			NAPILogHelper.logError(e);
			return null;
		}
	}

	@Internal
	private static final List<Class> findClasses(File directory, String packageName)
	{
		try
		{
			List<Class> classes = new ArrayList<>();

			if (!directory.exists())
			{
				return classes;
			}

			File[] files = directory.listFiles();

			for (File file : files)
			{
				if (file.isDirectory() && !file.getName().contains("."))
				{
					classes.addAll(findClasses(file, packageName + "." + file.getName()));
				}
				else if (file.getName().endsWith(".class"))
				{
					classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
				}
			}
			return classes;
		}
		catch (ClassNotFoundException e)
		{
			NAPILogHelper.logError(e);
			return null;
		}
	}

	static
	{
		getAllLoadedClassNames();
	}
}
