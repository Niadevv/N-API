package co.uk.niadel.api.events.worldevents;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import co.uk.niadel.api.events.EventBase;
import co.uk.niadel.api.events.IEvent;

/**
 * You can use this event to transport players to another dimension when they sleep and wake up (AKA Nightmares/Dreams).
 * @author Niadel
 */
public class EventAllPlayersAwoken extends EventBase implements IEvent 
{
	/**
	 * A list of all players who were woken up.
	 */
	public List<EntityPlayer> awokenPlayers;
	
	public EventAllPlayersAwoken(List<EntityPlayer> players)
	{
		this.awokenPlayers = players;
	}
}
