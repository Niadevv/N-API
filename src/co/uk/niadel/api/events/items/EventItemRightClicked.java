package co.uk.niadel.api.events.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import co.uk.niadel.api.events.EventBase;
import co.uk.niadel.api.events.IEvent;

/**
 * Fired when you right click an item as the name suggests.
 * @author Niadel
 *
 */
public class EventItemRightClicked extends EventBase implements IEvent
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
	public void initEvent()
	{
		addData(new Object[] {"EventItemRightClicked", this, this.clickedItem, this.world, this.player});
	}
}
