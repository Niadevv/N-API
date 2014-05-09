package co.uk.niadel.api.forgewrapper;

import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import co.uk.niadel.api.events.EventsList;
import co.uk.niadel.api.events.entity.EventEntityDeath;
import co.uk.niadel.api.events.entity.EventEntityStruckByLightning;
import co.uk.niadel.api.events.server.EventServerChat;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

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
}
