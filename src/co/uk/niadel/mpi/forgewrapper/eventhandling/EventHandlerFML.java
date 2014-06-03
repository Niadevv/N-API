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
		EventsList.fireEvent(new EventWorldTicked(event.world));
	}
}
