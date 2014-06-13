package co.uk.niadel.mpi.events.world;

import net.minecraft.world.World;

/**
 * This is fired when the world is ticked. As you can do a lot of stuff with a world object,
 * this is quite powerful.
 * @author Niadel
 */
public class EventWorldTicked
{
	/**
	 * The world that is ticked.
	 */
	public World world;
	
	public EventWorldTicked(World world)
	{
		this.world = world;
	}
}
