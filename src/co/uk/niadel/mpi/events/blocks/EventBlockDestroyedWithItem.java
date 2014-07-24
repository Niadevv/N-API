package co.uk.niadel.mpi.events.blocks;

import co.uk.niadel.mpi.events.IEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Can be used for custom pickaxes, potentially. Or maybe a different type of Fortune?
 * @author Niadel
 *
 */
public class EventBlockDestroyedWithItem implements IEvent
{
	/**
	 * The item used to destroy the block.
	 */
	public ItemStack itemDestroying;
	
	/**
	 * A world object.
	 */
	public World world;
	
	/**
	 * The block that's destroyed.
	 */
	public Block destroyedBlock;
	
	/**
	 * The coords of the block.
	 */
	public int blockX, blockY, blockZ;
	
	/**
	 * The entity who destroyed the block.
	 */
	public EntityLivingBase destroyer;
	
	public EventBlockDestroyedWithItem(ItemStack itemDestroying, World world, Block destroyedBlock, int blockX, int blockY, int blockZ, EntityLivingBase destroyer)
	{
		this.itemDestroying = itemDestroying;
		this.world = world;
		this.destroyedBlock = destroyedBlock;
		this.blockX = blockX;
		this.blockY = blockY;
		this.blockZ = blockZ;
		this.destroyer = destroyer;
	}
}
