package co.uk.niadel.api.events.entity;

import net.minecraft.entity.Entity;
import co.uk.niadel.api.events.EventBase;
import co.uk.niadel.api.events.IEvent;

public class EventEntitySpawned extends EventBase implements IEvent
{
	Entity entity;
	
	public EventEntitySpawned(Entity entity) 
	{
		this.entity = entity;
	}

	@Override
	public void initEvent()
	{
		addData(new Object[] {"EventEntitySpawned", this, this.entity});
	}
}
