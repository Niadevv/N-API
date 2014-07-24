package co.uk.niadel.mpi.events.server;

import co.uk.niadel.mpi.events.IEvent;

/**
 * Fired when the server chats.
 * @author Niadel
 *
 */
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
}
