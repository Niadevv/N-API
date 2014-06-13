package co.uk.niadel.mpi.events;

/**
 * All events that are cancellable extend this.
 * @author Niadel
 *
 */
public abstract class EventCancellable
{
	public boolean cancelled = false;
	
	/**
	 * Sets an event as being cancelled.
	 * @param cancel
	 */
	public void setCancelled(boolean cancel)
	{
		this.cancelled = cancel;
	}
	
	/**
	 * Gets whether or not this event has been cancelled.
	 */
	public boolean isCancelled()
	{
		return this.cancelled;
	}
}
