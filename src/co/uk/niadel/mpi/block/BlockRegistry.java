package co.uk.niadel.mpi.block;

import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.RegistrySimple;
import co.uk.niadel.mpi.annotations.MPIAnnotations.RecommendedMethod;
import co.uk.niadel.mpi.annotations.VersionMarkingAnnotations.TestFeature;
import co.uk.niadel.mpi.util.reflection.ReflectionManipulateValues;
import co.uk.niadel.mpi.util.UniqueIdAcquirer;

/**
 * Where you register blocks.
 * @author Niadel
 */
public final class BlockRegistry
{
	/**
	 * The block registry itself, from vanilla so you know it works.
	 */
	public static final RegistryNamespaced registry = Block.blockRegistry;
	
	/**
	 * Used for getting unique ids.
	 */
	public static final UniqueIdAcquirer numAcquirer = new UniqueIdAcquirer(); 
	
	/**
	 * Adds a standard block with the same method used in Block.java. I think you specify 
	 * the namespace in the nonNumericId, but don't hold me to that.
	 * 
	 * @param numericId
	 * @param nonNumericId
	 * @param block
	 */
	public static final void addBlock(String nonNumericId, Block block)
	{
		registry.addObject(numAcquirer.nextId(nonNumericId), nonNumericId, block);
	}
	
	/**
	 * Replaces a block with another block. May not work.
	 * @param numericId
	 * @param nonNumericId
	 * @param originalBlock
	 * @param newBlock
	 */
	@TestFeature(firstAppearance = "1.0")
	public static final void replaceBlock(String nonNumericId, Block newBlock)
	{
		Map blocksMap = ReflectionManipulateValues.getValue(RegistrySimple.class, Block.blockRegistry, "registryObjects");
		blocksMap.remove(nonNumericId);
		blocksMap.put(nonNumericId, newBlock);
		
		ReflectionManipulateValues.setValue(RegistrySimple.class, Block.blockRegistry, "registryObjects", blocksMap);
	}
}
