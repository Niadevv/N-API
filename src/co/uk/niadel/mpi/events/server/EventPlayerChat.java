package co.uk.niadel.mpi.events.server;

import co.uk.niadel.mpi.events.IEvent;
import net.minecraft.entity.player.EntityPlayer;
import co.uk.niadel.mpi.events.EventCancellable;

/**
 * Fired when a <b>player<b> chats. Can be used to censor words.
 * @author Niadel
 *
 */
public class EventPlayerChat extends EventCancellable implements IEvent
{
	public EntityPlayer player;
	public String text;

	public EventPlayerChat(EntityPlayer player, String text)
	{
		this.player = player;
		this.text = text;
	}
}
