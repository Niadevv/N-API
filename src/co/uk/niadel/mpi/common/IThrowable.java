package co.uk.niadel.mpi.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * All throwables should implement this, well from mods anyways, I don't like to touch
 * vanilla unnecessarily.
 * 
 * This interface allows for easy implementation of throwing items and cleaner onItemRightClick code.
 * @author Niadel
 *
 */
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
