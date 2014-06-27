package co.uk.niadel.mpi.block;

import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.RegistrySimple;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Dangerous;
import co.uk.niadel.mpi.annotations.MPIAnnotations.RecommendedMethod;
import co.uk.niadel.mpi.annotations.VersionMarkingAnnotations.TestFeature;
import co.uk.niadel.mpi.util.reflection.ReflectionManipulateValues;

/**
 * Where you register blocks.
 * @author Niadel
 */
public final class BlockRegistry
{
	/**
	 * The block registry itself, from vanilla so you know it works.
	 */
	public static RegistryNamespaced registry = Block.blockRegistry;
	
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
	
	/**
	 * Replaces a block with another block. May not work.
	 * @param numericId
	 * @param nonNumericId
	 * @param originalBlock
	 * @param newBlock
	 */
	@TestFeature(firstAppearance = "1.0")
	public static void replaceBlock(String nonNumericId, Block newBlock)
	{
		Map blocksMap = ReflectionManipulateValues.getValue(RegistrySimple.class, Block.blockRegistry, "registryObjects");
		blocksMap.remove(nonNumericId);
		blocksMap.put(nonNumericId, newBlock);
		
		ReflectionManipulateValues.setValue(RegistrySimple.class, Block.blockRegistry, "registryObjects", blocksMap);
	}
}
