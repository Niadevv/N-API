package co.uk.niadel.api.events;

import java.util.ArrayList;
import java.util.List;
import co.uk.niadel.api.annotations.VersionMarkingAnnotations.TestFeature;

@TestFeature(stable = false, firstAppearance = "1.0")
/**
 * The Event object, what is actually put into the list. <b><u>NOT TO BE CONFUSED
 * WITH EventBase AND IT'S DESCENDANTS!</u></b>
 * @author Niadel
 *
 */
public class Event
{
	/**
	 * Any bonus data, like where the event originated from, or the
	 * entity that damaged another entity in the case of EventOnEntityDamaged.
	 */
	public List<Object[]> data = new ArrayList<>();
	
	/**
	 * The event's name, eg. "EventOnPlayerDamaged".
	 */
	public String eventName;
	
	/**
	 * Registers the event to the list of Events in EventsList.
	 */
	public final void fireEvent()
	{
		EventBase.addEvent(this);
	}
	
	@Deprecated
	/**
	 * Please use addData. It results in cleaner code. Only use this if you need to delete
	 * the data field.
	 * 
	 * @deprecated addData doesn't replace the entire data, so this is a better alternative.
	 * @param newData
	 */
	public final void setData(List<Object[]> newData)
	{
		this.data = newData;
	}
	
	/**
	 * Sets the Event's name.
	 * @param name
	 */
	public final void setName(String name)
	{
		this.eventName = name;
	}
	
	/**
	 * Constructs a new Event
	 * @param data The default List of Object[]s to use.
	 * @param name
	 */
	public Event(List<Object[]> data, String name)
	{
		this.data = data;
		this.eventName = name;
		fireEvent();
	}
	
	/**
	 * Returns the Event's name.
	 * @return
	 */
	public String getEventName()
	{
		return this.eventName;
	}
	
	/**
	 * Adds an Object[] of data to the Event's List of Object[]s.
	 * @param dataToAdd
	 */
	public final void addData(Object[] dataToAdd)
	{
		this.data.add(dataToAdd);
	}
}