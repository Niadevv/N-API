package co.uk.niadel.mpi.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IThrowable
{
	/**
	 * Where you do the work of throwing an item.
	 * @param item
	 * @param world
	 * @param thrower
	 */
	public void throwItem(ItemStack item, World world, EntityPlayer thrower);
}
