package co.uk.niadel.mpi.asm;

import java.util.Map;
import co.uk.niadel.mpi.util.ByteManipulationUtils;
import co.uk.niadel.mpi.util.NAPILogHelper;

/**
 * Interface implemented by all ASM transformers.
 *
 * @author Niadel
 */
public interface IASMTransformer
{	
	/**
	 * Where you manipulate the bytecodes themselves - passedBytes is a Map of the
	 * byte[]s of the classes you requested in requestTransformedClasses. You can use ClassReader's String based constructor.
	 * @param className The fully qualified name of the curent class being transformed.
	 * @return The modified bytes.
	 * 
	 */
	public byte[] manipulateBytecodes(String className);
	
	/**
	 * Necessary for the transformer to work, this is where you tell the loader that
	 * you're transforming these classes by returning the fully qualified names of them:
	 * 
	 * Eg. net.minecraft.block.Block, 
	 * net.minecraft.entity.Entity, etc.
	 * @return The classes that you want transformed.
	 */
	public String[] requestTransformedClasses();
}
