package co.uk.niadel.mpi.asm;

import co.uk.niadel.mpi.annotations.IAnnotationHandler;

/**
 * Adds utilities for N-API features and primarily exists to minimise Reflection use where it will be called a lot.
 */
public class NAPIASMUtilsTransformer implements IASMTransformer
{
	@Override
	public byte[] manipulateBytecodes(String className)
	{
		/**
		try
		{
			if (Class.forName(className).newInstance() instanceof IAnnotationHandler)
			{
				this.modifyAnnotationHandler(className, bytes);
			}
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		*/

		return null;
	}

	@Override
	public String[] requestTransformedClasses()
	{
		return new String[] {};
	}
}
