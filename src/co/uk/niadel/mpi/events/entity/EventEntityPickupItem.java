package co.uk.niadel.mpi.events.entity;

import net.minecraft.entity.Entity;

/**
 *	Fired when an entity picks up an item.
 */
public class EventEntityPickupItem
{
	public Entity entity;

	public int stackSize;

	public EventEntityPickupItem(Entity entity, int stackSize)
	{
		this.entity = entity;
		this.stackSize = stackSize;
	}
}
