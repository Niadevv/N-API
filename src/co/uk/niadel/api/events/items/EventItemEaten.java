package co.uk.niadel.api.events.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import co.uk.niadel.api.events.EventBase;
import co.uk.niadel.api.events.IEvent;

/**
 * Fired when a player eats something.
 * @author Niadel
 *
 */
public class EventItemEaten extends EventBase implements IEvent
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
	
	@Override
	public void initEvent()
	{
		addData(new Object[] {"EventItemEaten", this, this.itemStack, this.world, this.player});
	}
}
