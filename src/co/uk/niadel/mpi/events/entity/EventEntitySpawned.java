package co.uk.niadel.mpi.events.entity;

import net.minecraft.entity.Entity;
import co.uk.niadel.mpi.events.EventCancellable;

public class EventEntitySpawned extends EventCancellable
{
	Entity entity;
	
	public EventEntitySpawned(Entity entity) 
	{
		this.entity = entity;
	}
}
