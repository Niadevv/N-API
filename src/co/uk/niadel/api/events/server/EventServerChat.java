package co.uk.niadel.api.events.server;

import co.uk.niadel.api.events.EventBase;
import co.uk.niadel.api.events.IEvent;

public class EventServerChat extends EventBase implements IEvent
{
	/**
	 * The message sent by chat.
	 */
	public String message;
	
	public EventServerChat(String message)
	{
		this.message = message;
	}
	
	@Override
	public void initEvent()
	{
		addData(new Object[] {"EventServerChat", this, this.message});
	}
}
