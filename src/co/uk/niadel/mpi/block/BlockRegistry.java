package co.uk.niadel.mpi.block;

import java.util.HashMap;
import java.util.Map;

import co.uk.niadel.mpi.modhandler.ModRegister;
import co.uk.niadel.mpi.util.MCData;
import co.uk.niadel.mpi.util.reflection.ReflectionCallMethods;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.init.Blocks;
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
	 * A list of blocks added from mods that are flammable.
	 */
	public static final Map<String, Block> modFlammableBlocks = new HashMap<>();
	
	/**
	 * Adds a standard block with the same method used in Block.java. I think you specify 
	 * the namespace in the nonNumericId, but don't hold me to that. Handles numeric ids
	 * internally.
	 *
	 * @param nonNumericId
	 * @param block
	 */
	public static final void addBlock(String nonNumericId, Block block)
	{
		registry.addObject(ModRegister.idAcquirer.nextId(nonNumericId), nonNumericId, block);
	}
	
	/**
	 * Replaces a block with another block. May not work.
	 * @param nonNumericId
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

	/**
	 * Makes a block burnable by fire.
	 * @param block The block that will be set to burn.
	 * @param flammabillity How easily this block catches fire. Higher this is = more likely.
	 * @param chanceToNotBurn How likely this block is to be destroyed. Higher this is = less likely it is to be destroyed.
	 */
	public static final void addFlammableBlock(Block block, int flammabillity, int chanceToNotBurn)
	{
		if (!MCData.isForge)
		{
			ReflectionCallMethods.callMethod(BlockFire.class, "func_149842_a", Blocks.fire.getIdFromBlock(block), flammabillity, chanceToNotBurn);
		}
		else
		{
			//Forge compat.
			ReflectionCallMethods.callMethod(BlockFire.class, "setFireInfo", block, flammabillity, chanceToNotBurn);
		}
	}
}
