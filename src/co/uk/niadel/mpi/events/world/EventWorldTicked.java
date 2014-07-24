package co.uk.niadel.mpi.events.world;

import java.util.Iterator;

import co.uk.niadel.mpi.events.IEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import co.uk.niadel.mpi.events.EventsList;

/**
 * This is fired when the world is ticked. As you can do a lot of stuff with a world object,
 * this is quite powerful. It's also the most complex in terms of events.
 * @author Niadel
 */
public class EventWorldTicked implements IEvent
{
	/**
	 * The world that is ticked.
	 */
	public World world;
	
	public EventWorldTicked(World world)
	{
		this.world = world;
		tickEntities();
		tickPlayers();
	}
	
	public void tickEntities()
	{
		Iterator<? extends Entity> entityIterator = this.world.loadedEntityList.iterator();
		
		while (entityIterator.hasNext())
		{
			EventsList.fireEvent(entityIterator.next());
		}
	}
	
	public void tickPlayers()
	{
		Iterator<? extends EntityPlayer> playerIterator = this.world.playerEntities.iterator();
		
		while (playerIterator.hasNext())
		{
			EventsList.fireEvent(new EventPlayerTicked(playerIterator.next()));
		}
	}
}
