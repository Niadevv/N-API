package co.uk.niadel.napi.events.entity;

import co.uk.niadel.napi.events.IEvent;
import net.minecraft.entity.Entity;

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
}
