package co.uk.niadel.mpi.events.entity;

import co.uk.niadel.mpi.events.IEvent;
import net.minecraft.entity.Entity;
import co.uk.niadel.mpi.events.EventCancellable;

public class EventEntityDeath extends EventCancellable implements IEvent
{
	/**
	 * The entity that died.
	 */
	public Entity entity;
	
	public EventEntityDeath(Entity entity)
	{
		this.entity = entity;
	}
}
