package co.uk.niadel.api.events.entity;

import net.minecraft.entity.Entity;
import co.uk.niadel.api.events.EventCancellable;
import co.uk.niadel.api.events.IEvent;

public class EventEntityDeath extends EventCancellable implements IEvent
{
	public Entity entity;
	
	public EventEntityDeath(Entity entity)
	{
		this.entity = entity;
	}

	@Override
	public String getName()
	{
		return "EventEntityDeath";
	}
}
