package co.uk.niadel.mpi.events.world;

import co.uk.niadel.mpi.events.IEvent;
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
