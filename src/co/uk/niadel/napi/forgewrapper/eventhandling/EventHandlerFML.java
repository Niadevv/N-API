package co.uk.niadel.napi.forgewrapper.eventhandling;

import co.uk.niadel.napi.common.NAPIData;
import co.uk.niadel.napi.events.EventFactory;
import co.uk.niadel.napi.events.world.EventPlayerTicked;
import co.uk.niadel.napi.events.world.EventWorldTicked;
import co.uk.niadel.napi.forgewrapper.NAPIMod;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
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
		EventFactory.fireEvent(new EventWorldTicked(event.world));
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onPlayerTick(PlayerTickEvent event)
	{
		EventFactory.fireEvent(new EventPlayerTicked(event.player));
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onConfigChanged(OnConfigChangedEvent event)
	{
		if (event.modID == NAPIData.FORGE_MODID)
		{
			NAPIMod.updateConfig();
		}
	}
}
