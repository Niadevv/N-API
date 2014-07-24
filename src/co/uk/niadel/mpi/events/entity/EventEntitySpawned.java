package co.uk.niadel.mpi.events.entity;

import co.uk.niadel.mpi.events.IEvent;
import net.minecraft.entity.Entity;
import co.uk.niadel.mpi.events.EventCancellable;

public class EventEntitySpawned extends EventCancellable implements IEvent
{
	Entity entity;
	
	public EventEntitySpawned(Entity entity) 
	{
		this.entity = entity;
	}
}
