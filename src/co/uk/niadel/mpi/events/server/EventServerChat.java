package co.uk.niadel.mpi.events.server;

/**
 * Fired when the server chats.
 * @author Niadel
 *
 */
public class EventServerChat
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
