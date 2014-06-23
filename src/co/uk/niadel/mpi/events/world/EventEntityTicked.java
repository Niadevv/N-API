package co.uk.niadel.mpi.events.world;

import net.minecraft.entity.Entity;

public class EventEntityTicked
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
