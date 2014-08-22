package co.uk.niadel.mpi.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
public interface IThrowableItem
{
	/**
	 * Test whether or not the item can be thrown. The world will ALWAYS be serverside here.
	 * @param item The ItemStack of the item right clicked to be thrown.
	 * @param world The world object.
	 * @param thrower The entity that is throwing it.
	 * @return Whether or not the item can be thrown.
	 */
	public boolean canThrowItem(ItemStack item, World world, EntityPlayer thrower);

	/**
	 * Override and return an entity which will be thrown. It's best to instantiate it directly.
	 * @param world The world object.
	 * @param thrower The entity that is throwing it.
	 * @return The entity that will be thrown.
	 */
	public Entity getThrownEntity(World world, EntityPlayer thrower);
}
