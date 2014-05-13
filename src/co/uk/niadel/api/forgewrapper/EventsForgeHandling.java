package co.uk.niadel.api.forgewrapper;

import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import co.uk.niadel.api.events.EventsList;
import co.uk.niadel.api.events.entity.EventEntityDeath;
import co.uk.niadel.api.events.entity.EventEntitySpawned;
import co.uk.niadel.api.events.entity.EventEntityStruckByLightning;
import co.uk.niadel.api.events.server.EventServerChat;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Used so I don't have to ASM the main few events in.
 * @author Niadel
 *
 */
public class EventsForgeHandling
{
	@SubscribeEvent
	public void onServerChat(ServerChatEvent event)
	{
		EventsList.fireEvent(new EventServerChat(event.message), "EventServerChatEvent");
	}
	
	@SubscribeEvent
	public void onEntityStruckByLightning(EntityStruckByLightningEvent event)
	{
		EventsList.fireEvent(new EventEntityStruckByLightning(event.lightning, event.entity), "EventEntityStruckByLightningEvent");
	}
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		EventsList.fireEvent(new EventEntityDeath(event.entity), "EventEntityDeath");
	}
	
	@SubscribeEvent
	public void onEntitySpawned(LivingSpawnEvent event)
	{
		EventsList.fireEvent(new EventEntitySpawned(event.entity), "EntityJoinWorldEvent");
	}
}
