package co.uk.niadel.mpi.asm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.mpi.util.ByteManipulationUtils;

public final class ASMRegistry 
{
	/**
	 * The list of ASM transformers registered.
	 */
	public static List<IASMTransformer> asmTransformers = new ArrayList<>();
	
	/**
	 * A list of fully qualified class names that cannot be transformed, at least via the
	 * N-API approved method.
	 */
	public static List<String> excludedClasses = new ArrayList<>();
	
	/**
	 * Adds an ASM transformer to the registry.
	 * @param transformer
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
		try
		{
			Iterator<IASMTransformer> asmIterator = asmTransformers.iterator();

			while (asmIterator.hasNext())
			{
				IASMTransformer currTransformer = (IASMTransformer) asmIterator.next();
				String[] requestedClasses = currTransformer.requestTransformedClasses();
				Map<String, byte[]> bytesMap = new HashMap<>();

				for (String currClassName : requestedClasses)
				{
					if (!(currTransformer.getClass().getName() == NAPIASMTransformer.class.getName()))
					{
						/*Don't allow users to edit the N-API files themselves unless it's NAPIASMTransformer, 
						 * if they do, they could break a LOT of stuff. If they want a feature, they can ask for it
						 * on either the post or the GitHub (or do a PR on the GitHub). Only I 
						 * should edit N-API's files, really, mainly because I have some grasp of 
						 * what I'm doing - Anyone else touching N-API will likely kill all mods using N-API.*/
						if (!currClassName.startsWith("co.uk.niadel.mpi"))
						{
							bytesMap.put(currClassName, ByteManipulationUtils.toByteArray(Class.forName(currClassName)));
						}
					}
					else
					{
						bytesMap.put(currClassName, ByteManipulationUtils.toByteArray(Class.forName(currClassName)));
					}
				}
				
				Iterator<String> nameIterator = bytesMap.keySet().iterator();
				Iterator<Entry<String, byte[]>> bytesIterator = bytesMap.entrySet().iterator();
				
				while (nameIterator.hasNext())
				{
					String currClassName = nameIterator.next();
					byte[] transformedBytes = currTransformer.manipulateBytecodes(currClassName, bytesIterator.next().getValue());
					NModLoader.defineClass(currClassName, transformedBytes);
				}
			}
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a class to exclude from transforming.
	 * @param excludedName
	 */
	public static final void addASMClassExclusion(String excludedName)
	{
		excludedClasses.add(excludedName);
	}
}
