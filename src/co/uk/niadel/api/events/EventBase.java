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
	/**
	 * A list of all of the events added. Used to get the last event fired.
	 */
	public static Event[] events = new Event[] {};
	
	/**
	 * The data to add when the event is fired.
	 */
	public List<Object[]> data = new ArrayList<>();
	
	/**
	 * Adds the event after clearing the events list if it contains more
	 * than 100 events.
	 * @param eventToAdd
	 */
	public static final void addEvent(Event eventToAdd)
	{
		if (events.length > 100)
		{
			clearEvents();
		}
		
		events[events.length - 1] = eventToAdd;
	}
	
	/**
	 * Gets the last executed event.
	 * @param event
	 * @return
	 */
	public static final Event currentEvent(Event event)
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
	
	/**
	 * Gets this event's data.
	 * @return
	 */
	public final List<Object[]> getData()
	{
		return this.data;
	}
	
	/**
	 * Appends more data.
	 * @param data
	 * @return
	 */
	public final IEvent addData(Object[] data)
	{
		this.data.add(data);
		return this;
	}
}
