package co.uk.niadel.napi.forgewrapper.eventhandling;

import co.uk.niadel.napi.events.EventFactory;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import co.uk.niadel.napi.events.EventCancellable;
import co.uk.niadel.napi.events.blocks.EventBlockDestroyedWithItem;
import co.uk.niadel.napi.events.entity.EventEntityDeath;
import co.uk.niadel.napi.events.entity.EventEntitySpawned;
import co.uk.niadel.napi.events.entity.EventEntityStruckByLightning;
import co.uk.niadel.napi.events.items.EventItemDespawned;
import co.uk.niadel.napi.events.server.EventPlayerChat;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Used so I don't have to ASM as many events in. All event priorities are set to highest
 * as MPIs take precedence over mods.
 * @author Niadel
 *
 */
public final class EventHandlerForge
{
	/**
	 * Was meant to be cancellable, but Forge's BlockBreakEvent doesn't allow cancelling u.u.
	 * @param event Forge's BreakEvent class.
	 */
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockEvent.BreakEvent event)
	{
		EventFactory.fireEvent(new EventBlockDestroyedWithItem(event.getPlayer().getCurrentEquippedItem(), event.world, event.block, event.x, event.y, event.z, event.getPlayer()));
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onServerChat(ServerChatEvent event)
	{
		EventFactory.fireEvent(new EventPlayerChat(event.player, event.message));
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEntityStruckByLightning(EntityStruckByLightningEvent event)
	{
		EventFactory.fireEvent(new EventEntityStruckByLightning(event.lightning, event.entity));
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEntityDeath(LivingDeathEvent event)
	{
		EventCancellable deathEvent = new EventEntityDeath(event.entity);
		
		EventFactory.fireEvent(deathEvent);
		
		if (deathEvent.isCancelled())
		{
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEntitySpawned(LivingSpawnEvent event)
	{
		EventCancellable eventEntitySpawn = new EventEntitySpawned(event.entity);
		EventFactory.fireEvent(eventEntitySpawn);
		
		if (eventEntitySpawn.isCancelled())
		{
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onItemDespawned(ItemExpireEvent event)
	{
		EventCancellable despawnEvent = new EventItemDespawned(event.entityItem);
		EventFactory.fireEvent(despawnEvent);
		
		if (despawnEvent.isCancelled())
		{
			//COUGH COUGH WRONG SPELLING OF CANCELLED COUGH COUGH #blamelex (Just kidding :P Not about the bad spelling, Cancelled is
			// spelt with two ls)
			//Also note that isCancelled is spelt correctly.
			event.setCanceled(true);
		}
	}
}
