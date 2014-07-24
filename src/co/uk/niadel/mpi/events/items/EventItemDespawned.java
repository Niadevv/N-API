package co.uk.niadel.mpi.events.items;

import co.uk.niadel.mpi.events.IEvent;
import net.minecraft.entity.item.EntityItem;
import co.uk.niadel.mpi.events.EventCancellable;

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
