package co.uk.niadel.api.events.apievents;

import co.uk.niadel.api.events.EventBase;
import co.uk.niadel.api.events.IEvent;

/**
 * Fired when a mod is loaded. No real point to this however, as before this point the mod that uses this may not have been loaded.
 * It was one of the original events to be created - in fact, likely the first.
 * @author Niadel
 *
 */
public class EventLoadMod extends EventBase implements IEvent
{
	public String modId;
	
	public EventLoadMod(String binaryName) 
	{
		this.modId = binaryName;
	}

	@Override
	public String getName()
	{
		return "EventLoadMod";
	}
}
