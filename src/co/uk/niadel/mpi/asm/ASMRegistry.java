package co.uk.niadel.mpi.asm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.mpi.util.ArrayUtils;
import co.uk.niadel.mpi.util.ByteManipulationUtils;

/**
 * Where your register stuff to do with ASM in N-API.
 */
public final class ASMRegistry 
{
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
			IASMTransformer currTransformer = asmIterator.next();
			String[] requestedClasses = currTransformer.requestTransformedClasses();
			//Map<String, byte[]> bytesMap = new HashMap<>();

			for (String currClassName : requestedClasses)
			{
				if (!(currTransformer.getClass().getName() == NAPIASMNecessityTransformer.class.getName()) && !(currTransformer.getClass().getName() == NAPIASMUtilsTransformer.class.getName()))
				{
					if (ASMRegistry.isClassExcluded(currClassName))
					{
						currTransformer.manipulateBytecodes(currClassName);
					}
				}
				else
				{
					currTransformer.manipulateBytecodes(currClassName);
				}
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
	}

	/**
	 * Adds a class to exclude from transforming. You can add important MPI classes to this.
	 * @param excludedName
	 */
	public static final void addASMClassExclusion(String excludedName)
	{
		excludedClasses.add(excludedName);
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
}
