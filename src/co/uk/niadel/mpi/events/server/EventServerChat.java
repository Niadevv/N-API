package co.uk.niadel.mpi.events.server;

import co.uk.niadel.mpi.events.IEvent;

public class EventServerChat implements IEvent
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
	public String getName()
	{
		return "EventServerChat";
	}
}
