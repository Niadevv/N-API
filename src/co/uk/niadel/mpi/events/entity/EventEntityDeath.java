package co.uk.niadel.mpi.events.entity;

import net.minecraft.entity.Entity;
import co.uk.niadel.mpi.events.EventCancellable;

public class EventEntityDeath extends EventCancellable
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
