package co.uk.niadel.mpi.events;

/**
 * All event handlers used by mods implement this.
 * @author Niadel
 *
 */
public interface IEventHandler 
{
	/**
	 * Where to handle an event. You will have to test the event type, but
	 * as you know Java you do know how to check it, <i>right?</i>
	 * @param event
	 */
	public void handleEvent(Object event);
}
