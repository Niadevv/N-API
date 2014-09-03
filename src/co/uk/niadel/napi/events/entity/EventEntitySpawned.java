package co.uk.niadel.napi.events.entity;

import co.uk.niadel.napi.events.IEvent;
import net.minecraft.entity.Entity;
import co.uk.niadel.napi.events.EventCancellable;

public class EventEntitySpawned extends EventCancellable implements IEvent
{
	Entity entity;
	
	public EventEntitySpawned(Entity entity) 
	{
		this.entity = entity;
	}
}
