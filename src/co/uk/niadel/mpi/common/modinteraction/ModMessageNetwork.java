package co.uk.niadel.mpi.common.modinteraction;

import java.util.ArrayList;
import java.util.List;

/**
 * Messages sent between mods are sent along this "network".
 *
 * @author Niadel
 */
public class ModMessageNetwork
{
	private List<ModMessageChannel> registeredChannels = new ArrayList<>();

	public void registerChannel(ModMessageChannel channel)
	{
		this.registeredChannels.add(channel);
	}

	/**
	 * Sends a message to this network.
	 * @param modidOfSender The modid of the mod sending the message to this network.
	 * @param modidOfReceiver The modid of the mod owning the target channel.
	 * @param message The actual message itself.
	 */
	public void sendMessageToChannel(String modidOfSender, String modidOfReceiver, IModMessage message)
	{
		for (ModMessageChannel channel : registeredChannels)
		{
			channel.sendMessage(modidOfSender, message);
		}
	}
}
