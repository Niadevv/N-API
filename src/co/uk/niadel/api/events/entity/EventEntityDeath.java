package co.uk.niadel.api.events.entity;

import co.uk.niadel.api.events.EventBase;
import co.uk.niadel.api.events.IEvent;

public class EventEntityDeath extends EventBase implements IEvent
{
	@Override
	public void initEvent() 
	{
		addData(new Object[] {"EventEntityDeath", this});
	}
	
}
