package co.uk.niadel.api.events.entity;

import net.minecraft.entity.player.EntityPlayer;
import co.uk.niadel.api.events.IEvent;

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
