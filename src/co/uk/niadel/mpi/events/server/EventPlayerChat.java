package co.uk.niadel.mpi.events.server;

import net.minecraft.entity.player.EntityPlayer;
import co.uk.niadel.mpi.events.EventCancellable;

/**
 * Fired when a <b>player<b> chats.
 * @author Niadel
 *
 */
public class EventPlayerChat extends EventCancellable
{
	private EntityPlayer player;
	private String text;

	public EventPlayerChat(EntityPlayer player, String text)
	{
		this.player = player;
		this.text = text;
	}
}
