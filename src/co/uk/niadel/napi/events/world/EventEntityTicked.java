package co.uk.niadel.napi.events.world;

import co.uk.niadel.napi.events.IEvent;
import net.minecraft.entity.Entity;

public class EventEntityTicked implements IEvent
{
	/**
	 * The ticked entity.
	 */
	public Entity theEntity;
	
	public EventEntityTicked(Entity theEntity)
	{
		this.theEntity = theEntity;
	}
}
