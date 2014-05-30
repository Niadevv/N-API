package co.uk.niadel.api.events.entity;

import net.minecraft.entity.Entity;
import co.uk.niadel.api.events.EventCancellable;
import co.uk.niadel.api.events.IEvent;

public class EventEntitySpawned extends EventCancellable implements IEvent
{
	Entity entity;
	
	public EventEntitySpawned(Entity entity) 
	{
		this.entity = entity;
	}

	@Override
	public String getName()
	{
		return "EventEntitySpawned";
	}
}
