package co.uk.niadel.api.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import co.uk.niadel.api.annotations.VersionMarkingAnnotations.TestFeature;

@TestFeature(stable = false, firstAppearance = "1.0")
/**
 * List of events. You can register an event in your ModRegister class. TBH, the Events
 * system is a tad, how would you say, <i>convoluted</i> at the moment, but this will likely change in later versions.
 * @author Niadel
 *
 */
public final class EventsList 
{
	/**
	 * List of all of the event handlers, indexed by a mod's ModID
	 */
	public static Map<String, IEventHandler> eventHandlers = new HashMap<>();
	
	
	/**
	 * Registers an event handler to eventHandlers. Important if you want to actually have
	 * your handler do anything!
	 * @param modName
	 * @param eventHandler
	 */
	public final static void registerEventHandler(String modName, IEventHandler eventHandler)
	{
		eventHandlers.put(modName, eventHandler);
	}
	
	/**
	 * Fires the event. Remember to create event with new and use the same event (Ideally,
	 * create a class like the Events class that contains the events you used and registered
	 * with)!
	 * 
	 * @param event
	 * @param name
	 * @return firedEvent
	 */
	public final static Event fireEvent(IEvent event, String name)
	{
		Event firedEvent = null;
		
	
		//Takes the data of event and transfers the data to a new Event object.
		List<Object[]> dataList = new ArrayList<>();
		dataList.add(event.data);
		firedEvent = new Event(dataList, name);
		
		Iterator handlerIterator = eventHandlers.entrySet().iterator();
		
		//Calls all event handlers' handleEvent methods as an event has been fired and the handlers
		//May need to handle it in order to do special stuff for their mod.
		callAllHandlers(event);
		
		return firedEvent;
	}
	
	/**
	 * Only really used to deal with cancellable events.
	 * @param event
	 */
	public final static IEvent callAllHandlers(IEvent event)
	{
		Iterator handlerIterator = eventHandlers.entrySet().iterator();
		
		while (handlerIterator.hasNext())
		{
			IEventHandler currHandler = (IEventHandler) handlerIterator.next();
			currHandler.handleEvent(event);
		}
		
		return event;
	}
	
	/**
	 * Returns an event's name.
	 * @param event
	 * @return
	 */
	public final static String getName(IEvent event)
	{
		return (String) event.data[0];
	}
}
