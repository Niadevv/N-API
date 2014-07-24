package co.uk.niadel.mpi.events.world;

import co.uk.niadel.mpi.events.IEvent;
import net.minecraft.entity.player.EntityPlayer;

public class EventPlayerTicked implements IEvent
{
	public EntityPlayer thePlayer;
	
	public EventPlayerTicked(EntityPlayer thePlayer)
	{
		this.thePlayer = thePlayer;
	}
}
