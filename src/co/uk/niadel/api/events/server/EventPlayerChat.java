package co.uk.niadel.api.events.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;
import co.uk.niadel.api.events.EventBase;
import co.uk.niadel.api.events.EventCancellable;
import co.uk.niadel.api.events.IEvent;

/**
 * This event is fired when a <b>player</b> chats, unlike EventServerChat.
 * @author Niadel
 *
 */
public class EventPlayerChat extends EventCancellable implements IEvent
{
	public EntityPlayer sender;
	public IChatComponent message;
	
	public EventPlayerChat(EntityPlayer sender, IChatComponent messageSent)
	{
		this.sender = sender;
		this.message = messageSent;
	}
	
	/**
	 * Returns the <b>player</b> chat as it would be in the chat.
	 * @return The message sent by the player as it would be in chat.
	 */
	public String getMessageText()
	{
		return this.message.getUnformattedTextForChat();
	}
	
	@Override
	public void initEvent()
	{
		this.addData(new Object[] {"EventPlayerChat", this, this.sender, this.message});
	}

}
