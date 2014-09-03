package co.uk.niadel.napi.events.world;

import co.uk.niadel.napi.events.IEvent;
import net.minecraft.entity.player.EntityPlayer;

public class EventPlayerTicked implements IEvent
{
	public EntityPlayer thePlayer;
	
	public EventPlayerTicked(EntityPlayer thePlayer)
	{
		this.thePlayer = thePlayer;
	}
}
