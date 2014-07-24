package co.uk.niadel.mpi.events.entity;

import co.uk.niadel.mpi.events.IEvent;
import net.minecraft.entity.player.EntityPlayer;

public class EventPlayerSpawned implements IEvent
{
	/**
	 * The player that spawns.
	 */
	EntityPlayer player;
	
	public EventPlayerSpawned(EntityPlayer player)
	{
		this.player = player;
	}
}
