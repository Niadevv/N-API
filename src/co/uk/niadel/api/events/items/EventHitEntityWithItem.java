package co.uk.niadel.api.events.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import co.uk.niadel.api.events.EventCancellable;
import co.uk.niadel.api.events.IEvent;

/**
 * Fired when an entity hits another with an item like a Sword. This allows for entities to only take damage from one type of item.
 * @author Daniel1
 *
 */
public class EventHitEntityWithItem extends EventCancellable implements IEvent
{
	public ItemStack item;
	public EntityLivingBase hitter, hitee;
	
	public EventHitEntityWithItem(ItemStack item, EntityLivingBase hitter, EntityLivingBase hitee)
	{
		this.item = item;
		this.hitter = hitter;
		this.hitee = hitee;
	}
	
	/**
	 * Gets the item from the ItemStack (Eg. if the ItemStack is that of a sword, this will return an ItemSword object).
	 * @return The Item Object mentioned above.
	 */
	public Item getItemType()
	{
		return this.item.getItem();
	}
}
