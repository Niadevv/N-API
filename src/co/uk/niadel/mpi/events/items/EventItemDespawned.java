package co.uk.niadel.mpi.events.items;

import net.minecraft.entity.item.EntityItem;
import co.uk.niadel.mpi.events.EventCancellable;
import co.uk.niadel.mpi.events.IEvent;

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

	@Override
	public String getName()
	{
		return "EventItemDespawned";
	}
}
