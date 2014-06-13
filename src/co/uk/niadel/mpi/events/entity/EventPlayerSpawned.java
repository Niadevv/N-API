package co.uk.niadel.mpi.events.entity;

import net.minecraft.entity.player.EntityPlayer;

public class EventPlayerSpawned
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
