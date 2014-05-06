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
	public static Event[] events = new Event[101];
	
	/**
	 * The data to add when the event is fired.
	 */
	public List<Object[]> data = new ArrayList<>();
	
	/**
	 * Adds the event after clearing the events list if it contains more
	 * than 100 events.
	 * @param eventToAdd
	 */
	public final static void addEvent(Event eventToAdd)
	{
		if (events.length > 100)
		{
			clearEvents();
		}
		
		events[events.length - 1] = eventToAdd;
	}
	
	public final static Event currentEvent(Event event)
	{
		return events[events.length - 1];
	}
	
	public final static void clearEvents()
	{
		for (int i = 0; i == events.length; i++)
		{
			events[i] = null;
		}
	}
	
	public final List<Object[]> getData(Event event)
	{
		return event.data;
	}
	
	public final List<Object[]> getEventBaseData()
	{
		return this.data;
	}
	
	public final IEvent addData(Object[] data)
	{
		this.data.add(data);
		return this;
	}
}
