package co.uk.niadel.mpi.events.world;

import net.minecraft.world.World;
import co.uk.niadel.mpi.events.IEvent;

/**
 * This is fired when the world is ticked.
 * @author Niadel
 */
public class EventWorldTicked implements IEvent 
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
