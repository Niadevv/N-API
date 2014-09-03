package co.uk.niadel.napi.events.entity;

import co.uk.niadel.napi.events.IEvent;
import net.minecraft.entity.Entity;
import co.uk.niadel.napi.events.EventCancellable;

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
