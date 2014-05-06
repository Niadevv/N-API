package co.uk.niadel.api.asm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ASMRegistry 
{
	public static List<IASMTransformer> asmTransformers = new ArrayList<>();
	
	public static final void addTransformer(IASMTransformer transformer)
	{
		asmTransformers.add(transformer);
	}
	
	/**
	 * Calls all register transformers' manipulateBytecodes method.
	 */
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
