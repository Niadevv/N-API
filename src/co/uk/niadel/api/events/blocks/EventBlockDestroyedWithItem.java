package co.uk.niadel.api.events.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import co.uk.niadel.api.events.IEvent;

public class EventBlockDestroyedWithItem implements IEvent
{
	public ItemStack itemDestroying;
	public World world;
	public Block destroyedBlock;
	public int blockX, blockY, blockZ;
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

	@Override
	public String getName()
	{
		return "EventBlockDestroyedWithItem";
	}
}
