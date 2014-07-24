package co.uk.niadel.mpi.events.entity;

import co.uk.niadel.mpi.events.IEvent;
import net.minecraft.entity.Entity;

/**
 * Fired when an entity's updateRidden method is called.
 */
public class EventUpdateRidden implements IEvent
{
	public Entity updatedEntity;

	public EventUpdateRidden(Entity theEntity)
	{
		this.updatedEntity = theEntity;
	}
}
