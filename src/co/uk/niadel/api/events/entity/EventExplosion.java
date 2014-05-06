package co.uk.niadel.api.events.entity;

import net.minecraft.entity.Entity;
import co.uk.niadel.api.events.EventBase;
import co.uk.niadel.api.events.IEvent;

public class EventExplosion extends EventBase implements IEvent
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
	public void initEvent()
	{
		addData(new Object[] {"EventExplosion", this, this.exploder, this.x, this.y, this.z});
	}
}
