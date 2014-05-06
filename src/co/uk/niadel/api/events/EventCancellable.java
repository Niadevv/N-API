package co.uk.niadel.api.events;

/**
 * All events that are cancellable extend this.
 * @author Niadel
 *
 */
public abstract class EventCancellable extends EventBase
{
	public boolean cancelled = false;
	
	public void setCancelled(boolean cancel)
	{
		this.cancelled = cancel;
	}
}
