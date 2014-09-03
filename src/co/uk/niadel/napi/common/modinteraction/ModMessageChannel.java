package co.uk.niadel.napi.common.modinteraction;

import java.util.ArrayList;
import java.util.List;

/**
 * Each mod that wishes to receive messages will have one of these registered to the main network (NAPIData.getModMessageNetwork())
 * and at least one message handler.
 *
 * @author Niadel
 */
public class ModMessageChannel
{
	/**
	 * The modid of the mod that this channel is owned by.
	 */
	public String owner;

	/**
	 * List of message handlers registered to this channel.
	 */
	public List<IModMessageHandler> messageHandlers = new ArrayList<>();

	public ModMessageChannel(String modid)
	{
		this.owner = modid;
	}

	/**
	 * Registers a mod message handler for this network.
	 * @param messageHandler The message handler to register.
	 */
	public void registerMessageHandler(IModMessageHandler messageHandler)
	{
		messageHandlers.add(messageHandler);
	}

	/**
	 * Sends a message on this channel.
	 * @param modidOfSender The modid of the mod sending the message.
	 * @param message The message to send.
	 */
	public void sendMessage(String modidOfSender, IModMessage message)
	{
		for (IModMessageHandler messageHandler : messageHandlers)
		{
			messageHandler.receiveMessage(modidOfSender, message);
		}
	}

	public String getModid()
	{
		return owner;
	}
}
