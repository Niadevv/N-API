package co.uk.niadel.mpi.forgewrapper.eventhandling;

import co.uk.niadel.mpi.events.EventsList;
import co.uk.niadel.mpi.events.world.EventPlayerTicked;
import co.uk.niadel.mpi.events.world.EventWorldTicked;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

/**
 * Separate class for handling FML gameevents.
 * @author Niadel
 *
 */
public final class EventHandlerFML
{
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onWorldTick(WorldTickEvent event)
	{
		//Update N-API isClient so Forge mods and N-API mods get along better.
		event.world.isClient = event.world.isRemote;
		EventsList.fireEvent(new EventWorldTicked(event.world));
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerTick(PlayerTickEvent event)
	{
		EventsList.fireEvent(new EventPlayerTicked(event.player));
	}
}
