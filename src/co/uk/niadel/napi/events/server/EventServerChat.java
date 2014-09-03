package co.uk.niadel.napi.events.server;

import co.uk.niadel.napi.events.IEvent;

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
