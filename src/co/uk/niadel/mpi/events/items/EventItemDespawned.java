package co.uk.niadel.mpi.events.items;

import net.minecraft.entity.item.EntityItem;
import co.uk.niadel.mpi.events.EventCancellable;

/**
 * Fired when an item despawns.
 * @author Niadel
 *
 */
public class EventItemDespawned extends EventCancellable
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
