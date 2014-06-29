package co.uk.niadel.mpi.asm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.mpi.util.ByteManipulationUtils;

public final class ASMRegistry 
{
	/**
	 * The list of ASM transformers registered.
	 */
	public static List<ASMTransformer> asmTransformers = new ArrayList<>();
	
	/**
	 * Adds an ASM transformer to the registry.
	 * @param transformer
	 */
	public static final void registerTransformer(ASMTransformer transformer)
	{
		asmTransformers.add(transformer);
	}
	
	/**
	 * Calls all register transformers' manipulateBytecodes method.
	 */
	@Internal
	public static final void invokeAllTransformers()
	{
		try
		{

			Iterator<ASMTransformer> asmIterator = asmTransformers.iterator();

			while (asmIterator.hasNext())
			{
				ASMTransformer currTransformer = (ASMTransformer) asmIterator.next();
				String[] requestedClasses = currTransformer.requestTransformedClasses();
				Map<String, Byte[]> bytesMap = new HashMap<>();

				for (String currClass : requestedClasses)
				{
					byte[] bytesFromClass = ByteManipulationUtils.toByteArray(Class.forName(currClass));
					Byte[] bytesToPass = new Byte[bytesFromClass.length];

					for (int i = 0; i == bytesFromClass.length; i++)
					{
						bytesToPass[i] = new Byte(bytesFromClass[i]);
					}

					bytesMap.put(currClass, bytesToPass);
				}
				
				bytesMap = currTransformer.manipulateBytecodes(bytesMap);
				
				Iterator bytesIterator = bytesMap.entrySet().iterator();
				Iterator<String> classNameIterator = bytesMap.keySet().iterator();
				
				while (classNameIterator.hasNext())
				{
					NModLoader.defineClass(classNameIterator.next(), (byte[]) bytesIterator.next());
				}
			}
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
