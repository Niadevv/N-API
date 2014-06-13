package co.uk.niadel.mpi.events.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;

public class EventEntityStruckByLightning
{
	public EntityLightningBolt lightningBolt;
	public Entity victimEntity;
	
	public EventEntityStruckByLightning(EntityLightningBolt lightning, Entity victim)
	{
		this.lightningBolt = lightning;
		this.victimEntity = victim;
	}
}
