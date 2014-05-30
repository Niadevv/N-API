package co.uk.niadel.api.events.worldevents;

import net.minecraft.world.World;
import co.uk.niadel.api.events.EventBase;
import co.uk.niadel.api.events.IEvent;

/**
 * This is fired when the world is ticked.
 * @author Niadel
 */
public class EventWorldTicked extends EventBase implements IEvent 
{
	public World world;
	
	public EventWorldTicked(World world)
	{
		this.world = world;
	}

	@Override
	public String getName()
	{
		return "EventWorldTicked";
	}
}
