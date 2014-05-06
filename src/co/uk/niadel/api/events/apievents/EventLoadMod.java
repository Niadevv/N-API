package co.uk.niadel.api.events.apievents;

import co.uk.niadel.api.events.EventBase;
import co.uk.niadel.api.events.IEvent;

/**
 * Fired when a mod is loaded.
 * @author Niadel
 *
 */
public class EventLoadMod extends EventBase implements IEvent
{
	private String modId;
	
	public EventLoadMod(String binaryName) 
	{
		this.modId = binaryName;
	}

	@Override
	public void initEvent()
	{
		addData(new Object[] {"EventLoadMod" + this.modId, this});
	}
}
