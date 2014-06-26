package co.uk.niadel.mpi.asm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;

public final class ASMRegistry 
{
	/**
	 * The list of ASM transformers registered.
	 */
	public static List<IASMTransformer> asmTransformers = new ArrayList<>();
	
	/**
	 * Adds an ASM transformer to the registry.
	 * @param transformer
	 */
	public static final void addTransformer(IASMTransformer transformer)
	{
		asmTransformers.add(transformer);
	}
	
	/**
	 * Calls all register transformers' manipulateBytecodes method.
	 */
	@Internal
	public static final void invokeAllTransformers()
	{
		Iterator<IASMTransformer> asmIterator = asmTransformers.iterator();
		
		while (asmIterator.hasNext())
		{
			IASMTransformer currTransformer = (IASMTransformer) asmIterator.next();
			currTransformer.manipulateBytecodes();
		}
	}
}
