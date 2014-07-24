package co.uk.niadel.mpi.events.entity;

import co.uk.niadel.mpi.events.IEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;

/**
 * Can be used to do things like turn an enderman into a creeper when it's hit by lightning.
 * @author Niadel
 *
 */
public class EventEntityStruckByLightning implements IEvent
{
	/**
	 * The lightning bolt itself.
	 */
	public EntityLightningBolt lightningBolt;
	
	/**
	 * The entity hit with lightning.
	 */
	public Entity victimEntity;
	
	public EventEntityStruckByLightning(EntityLightningBolt lightning, Entity victim)
	{
		this.lightningBolt = lightning;
		this.victimEntity = victim;
	}
}
