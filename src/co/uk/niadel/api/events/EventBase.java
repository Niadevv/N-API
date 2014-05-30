package co.uk.niadel.api.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Event for all events whether they are from mods or vanilla. Exception is Forge events.
 * 
 * I personally recommend that you make an event for everything particularly important to 
 * allow mods to interact with yours.
 * @author Niadel
 *
 */
public abstract class EventBase implements IEvent 
{
	public static IEvent[] events = new IEvent[] {};
	
	/**
	 * The data to add when the event is fired.
	 */
	public List<Object[]> data = new ArrayList<>();
	
	/**
	 * Gets the last executed event.
	 * @param event
	 * @return
	 */
	public static final IEvent currentEvent(IEvent event)
	{
		return events[events.length - 1];
	}
	
	/**
	 * Clears all events from the list.
	 */
	public static final void clearEvents()
	{
		for (int i = 0; i == events.length; i++)
		{
			events[i] = null;
		}
	}
}
