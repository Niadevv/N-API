package co.uk.niadel.api.events.entity;

import net.minecraft.entity.Entity;
import co.uk.niadel.api.events.IEvent;

public class EventExplosion implements IEvent
{
	Entity exploder;
	double x;
	double y;
	double z;

	public EventExplosion(Entity exploder, double explosionX, double explosionY, double explosionZ)
	{
		this.exploder = exploder;
		this.x = explosionX;
		this.y = explosionY;
		this.z = explosionZ;
	}

	@Override
	public String getName()
	{
		return "EventExplosion";
	}
}
