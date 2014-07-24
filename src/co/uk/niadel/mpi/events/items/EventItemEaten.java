package co.uk.niadel.mpi.events.items;

import co.uk.niadel.mpi.events.IEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Fired when a player eats something.
 * @author Niadel
 *
 */
public class EventItemEaten implements IEvent
{
	public ItemStack itemStack;
	public World world;
	public EntityPlayer player;
	
	public EventItemEaten(ItemStack itemStack, World world, EntityPlayer player)
	{
		this.itemStack = itemStack;
		this.world = world;
		this.player = player;
	}
}
