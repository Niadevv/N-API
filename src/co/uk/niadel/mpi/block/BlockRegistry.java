package co.uk.niadel.mpi.block;

import net.minecraft.block.Block;
import net.minecraft.util.RegistryNamespaced;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Dangerous;
import co.uk.niadel.mpi.annotations.MPIAnnotations.RecommendedMethod;

/**
 * Where you register blocks.
 * @author Niadel
 */
public final class BlockRegistry
{
	/**
	 * Used to prevent a ClassCastException.
	 */
	public static RegistryNamespaced registry = Block.getRegistry();
	
	/**
	 * Adds a standard block with the same method used in Block.java. I think you specify 
	 * the namespace in the nonNumericId, but don't hold me to that.
	 * 
	 * @param numericId
	 * @param nonNumericId
	 * @param block
	 */
	@RecommendedMethod
	public static void addBlock(int numericId, String nonNumericId, Block block)
	{
		registry.addObject(numericId + 2267, nonNumericId, block);
	}
	
	/**
	 * Adds a block without the numeric id being incremented by 2267. Can break lots of stuff.
	 * @param numericId
	 * @param nonNumericId
	 * @param block
	 */
	@Dangerous(reason = "Potentially compatability breaking.")
	public static void addBlockDangerously(int numericId, String nonNumericId, Block block)
	{
		registry.addObject(numericId, nonNumericId, block);
	}
}
