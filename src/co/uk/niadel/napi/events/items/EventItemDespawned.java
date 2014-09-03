package co.uk.niadel.napi.events.items;

import co.uk.niadel.napi.events.IEvent;
import net.minecraft.entity.item.EntityItem;
import co.uk.niadel.napi.events.EventCancellable;

/**
 * Fired when an item despawns.
 * @author Niadel
 *
 */
public class EventItemDespawned extends EventCancellable implements IEvent
{
	/**
	 * The item that has despawned.
	 */
	public EntityItem itemEntity;
	
	public EventItemDespawned(EntityItem item)
	{
		itemEntity = item;
	}
}
