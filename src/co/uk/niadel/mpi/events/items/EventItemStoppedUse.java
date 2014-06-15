package co.uk.niadel.mpi.events.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Fired when the player lets go of right click.
 * @author Niadel
 *
 */
public class EventItemStoppedUse
{
	/**
	 * The item that has stopped being in use.
	 */
	public ItemStack item;
	
	/**
	 * The world object.
	 */
	public World world;
	
	/**
	 * The player that stopped using the item.
	 */
	public EntityPlayer player;
	
	/**
	 * The time, likely in ticks, that the player used this item before it was stopped being
	 * in use.
	 */
	public int itemUsedTime;
	
	public EventItemStoppedUse(ItemStack item, World world, EntityPlayer player, int itemUsedTime)
	{
		this.item = item;
		this.world = world;
		this.player = player;
		this.itemUsedTime = itemUsedTime;
	}
}
