package co.uk.niadel.mpi.events.entity;

import net.minecraft.entity.player.EntityPlayer;
import co.uk.niadel.mpi.events.IEvent;

public class EventPlayerSpawned implements IEvent
{
	EntityPlayer player;
	
	public EventPlayerSpawned(EntityPlayer player)
	{
		this.player = player;
	}

	@Override
	public String getName()
	{
		return "EventPlayerSpawned";
	}
}
