package co.uk.niadel.napi.events.items;

import co.uk.niadel.napi.events.IEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Fired when you right click an item as the name suggests. Can be used for lots of 
 * different things.
 * @author Niadel
 *
 */
public class EventItemRightClicked implements IEvent
{
	/**
	 * The held item that was clicked.
	 */
	public ItemStack clickedItem;
	
	/**
	 * The world object.
	 */
	public World world;
	
	/**
	 * The player that clicked the item.
	 */
	public EntityPlayer player;
	
	public EventItemRightClicked(ItemStack item, World world, EntityPlayer player)
	{
		this.clickedItem = item;
		this.world = world;
		this.player = player;
	}
}
