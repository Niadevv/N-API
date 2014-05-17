package co.uk.niadel.api.events.entity;

import net.minecraft.entity.Entity;
import co.uk.niadel.api.events.EventBase;
import co.uk.niadel.api.events.IEvent;

public class EventEntityDeath extends EventBase implements IEvent
{
	public Entity entity;
	
	public EventEntityDeath(Entity entity)
	{
		this.entity = entity;
	}
}
