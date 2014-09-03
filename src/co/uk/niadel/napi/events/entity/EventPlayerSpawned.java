package co.uk.niadel.napi.events.entity;

import co.uk.niadel.napi.events.IEvent;
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
