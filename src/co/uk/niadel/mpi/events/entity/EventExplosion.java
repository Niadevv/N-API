package co.uk.niadel.mpi.events.entity;

import net.minecraft.entity.Entity;

public class EventExplosion
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
