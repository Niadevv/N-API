package co.uk.niadel.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.RegistryNamespaced;
import co.uk.niadel.api.annotations.MPIAnnotations.Internal;
import co.uk.niadel.api.annotations.MPIAnnotations.RecommendedMethod;

/**
 * Where you register blocks.
 * @author Niadel
 */
public final class BlockRegistry extends Block
{
	/**
	 * Used to prevent a ClassCastException.
	 */
	public static RegistryNamespaced registry = getRegistry();
	
	@Internal
	private BlockRegistry(Material material)
	{
		super(material);
	}

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
	public static void addBlockDangerously(int numericId, String nonNumericId, Block block)
	{
		registry.addObject(numericId, nonNumericId, block);
	}
}
