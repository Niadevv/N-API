package co.uk.niadel.mpi.events.world;

import net.minecraft.entity.player.EntityPlayer;

public class EventPlayerTicked
{
	public EntityPlayer thePlayer;
	
	public EventPlayerTicked(EntityPlayer thePlayer)
	{
		this.thePlayer = thePlayer;
	}
}
