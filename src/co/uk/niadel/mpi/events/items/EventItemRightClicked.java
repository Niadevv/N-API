package co.uk.niadel.mpi.events.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import co.uk.niadel.mpi.events.IEvent;

/**
 * Fired when you right click an item as the name suggests.
 * @author Niadel
 *
 */
public class EventItemRightClicked implements IEvent
{
	public ItemStack clickedItem;
	public World world;
	public EntityPlayer player;
	
	public EventItemRightClicked(ItemStack item, World world, EntityPlayer player)
	{
		this.clickedItem = item;
		this.world = world;
		this.player = player;
	}

	@Override
	public String getName()
	{
		return "EventItemRightClicked";
	}
}
