package co.uk.niadel.api.events.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import co.uk.niadel.api.events.IEvent;

public class EventEntityStruckByLightning implements IEvent
{
	public EntityLightningBolt lightningBolt;
	public Entity victimEntity;
	
	public EventEntityStruckByLightning(EntityLightningBolt lightning, Entity victim)
	{
		this.lightningBolt = lightning;
		this.victimEntity = victim;
	}

	@Override
	public String getName()
	{
		return "EventEntityStruckByLightning";
	}
}
