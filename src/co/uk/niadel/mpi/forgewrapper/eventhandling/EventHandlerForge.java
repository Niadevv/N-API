package co.uk.niadel.mpi.forgewrapper.eventhandling;

import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import co.uk.niadel.mpi.events.EventCancellable;
import co.uk.niadel.mpi.events.EventsList;
import co.uk.niadel.mpi.events.IEvent;
import co.uk.niadel.mpi.events.blocks.EventBlockDestroyedWithItem;
import co.uk.niadel.mpi.events.entity.EventEntityDeath;
import co.uk.niadel.mpi.events.entity.EventEntitySpawned;
import co.uk.niadel.mpi.events.entity.EventEntityStruckByLightning;
import co.uk.niadel.mpi.events.items.EventItemDespawned;
import co.uk.niadel.mpi.events.server.EventPlayerChat;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Used so I don't have to ASM as many events in.
 * @author Niadel
 *
 */
public class EventHandlerForge
{
	/**
	 * Was meant to be cancellable, but Forge's BlockBreakEvent doesn't allow cancelling u.u.
	 * @param event
	 */
	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event)
	{
		EventsList.fireEvent(new EventBlockDestroyedWithItem(event.getPlayer().getCurrentEquippedItem(), event.world, event.block,event.x, event.y, event.z, event.getPlayer()));
	}
	
	@SubscribeEvent
	public void onServerChat(ServerChatEvent event)
	{
		EventsList.fireEvent(new EventPlayerChat(event.player, event.message));
	}
	
	@SubscribeEvent
	public void onEntityStruckByLightning(EntityStruckByLightningEvent event)
	{
		EventsList.fireEvent(new EventEntityStruckByLightning(event.lightning, event.entity));
	}
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		EventCancellable deathEvent = new EventEntityDeath(event.entity);
		
		EventsList.fireEvent(deathEvent);
		
		if (deathEvent.isCancelled())
		{
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onEntitySpawned(LivingSpawnEvent event)
	{
		EventCancellable eventEntitySpawn = new EventEntitySpawned(event.entity);
		
		EventsList.fireEvent(eventEntitySpawn);
		
		if (eventEntitySpawn.isCancelled())
		{
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onItemDespawned(ItemExpireEvent event)
	{
		EventCancellable despawnEvent = new EventItemDespawned(event.entityItem);
		EventsList.fireEvent(despawnEvent);
		
		if (despawnEvent.isCancelled())
		{
			//COUGH COUGH WRONG SPELLING OF CANCELLED COUGH COUGH #blamelex
			event.setCanceled(true);
		}
	}
}
