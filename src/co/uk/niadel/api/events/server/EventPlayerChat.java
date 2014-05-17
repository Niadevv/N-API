package co.uk.niadel.api.events.server;

import net.minecraft.entity.player.EntityPlayer;
import co.uk.niadel.api.events.EventCancellable;
import co.uk.niadel.api.events.IEvent;

/**
 * Fired when a <b>player<b> chats.
 * @author Niadel
 *
 */
public class EventPlayerChat extends EventCancellable implements IEvent
{
	private EntityPlayer player;
	private String text;

	public EventPlayerChat(EntityPlayer player, String text)
	{
		this.player = player;
		this.text = text;
	}
}
