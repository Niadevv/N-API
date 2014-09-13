package co.uk.niadel.napi.block;

import java.util.HashMap;
import java.util.Map;

import co.uk.niadel.napi.annotations.VersionMarkingAnnotations;
import co.uk.niadel.napi.modhandler.NAPIModRegister;
import co.uk.niadel.napi.util.MCData;
import co.uk.niadel.napi.util.reflection.ReflectionCallMethods;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFire;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.item.Item;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.util.RegistrySimple;
import co.uk.niadel.napi.util.reflection.ReflectionManipulateValues;

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
	 * Adds a standard block with the same method used in Block.java. Handles numeric ids
	 * internally.
	 *
	 * @param nonNumericId The string id of the block. Follow the vanilla convention of modid:block_name
	 * @param block The block object itself.
	 */
	public static final void addBlock(String nonNumericId, Block block)
	{
		registry.addObject(NAPIModRegister.idAcquirer.nextId(nonNumericId), nonNumericId, block);
	}
	
	/**
	 * Replaces a block with another block. May not work.
	 * @param nonNumericId The string id of the block to remove.
	 * @param newBlock The new Block to put in it's place.
	 */
	@VersionMarkingAnnotations.Experimental(firstAppearance = "1.0")
	public static final void replaceBlock(String nonNumericId, Block newBlock)
	{
		Map<String, Block> blocksMap = ReflectionManipulateValues.getValue(RegistrySimple.class, Block.blockRegistry, "registryObjects");
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
			ReflectionCallMethods.callMethod(BlockFire.class, "func_149842_a", Block.getIdFromBlock(block), flammabillity, chanceToNotBurn);
		}
		else
		{
			//Forge compat.
			ReflectionCallMethods.callMethod(BlockFire.class, "setFireInfo", block, flammabillity, chanceToNotBurn);
		}
	}

	/**
	 * Add a dispenser behaviour.
	 * @param itemDispensed Item to dispense.
	 * @param behaviour The behaviour itself.
	 */
	public static final void addDispenserBehaviour(Item itemDispensed, IBehaviorDispenseItem behaviour)
	{
		BlockDispenser.dispenseBehaviorRegistry.putObject(itemDispensed, behaviour);
	}
}
