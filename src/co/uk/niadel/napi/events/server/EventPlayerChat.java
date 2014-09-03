package co.uk.niadel.napi.events.server;

import co.uk.niadel.napi.events.IEvent;
import net.minecraft.entity.player.EntityPlayer;
import co.uk.niadel.napi.events.EventCancellable;

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
