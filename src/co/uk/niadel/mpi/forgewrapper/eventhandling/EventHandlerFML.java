package co.uk.niadel.mpi.forgewrapper.eventhandling;

import co.uk.niadel.mpi.events.EventsList;
import co.uk.niadel.mpi.events.world.EventWorldTicked;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

/**
 * Separate class for handling FML gameevents.
 * @author Niadel
 *
 */
public class EventHandlerFML
{
	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event)
	{
		//Update N-API isClient so Forge mods and N-API mods get along better.
		event.world.isClient = event.world.isRemote;
		EventsList.fireEvent(new EventWorldTicked(event.world));
	}
}
