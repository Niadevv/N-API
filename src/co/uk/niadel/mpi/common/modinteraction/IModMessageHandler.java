package co.uk.niadel.mpi.common.modinteraction;

/**
 * Implemented by message handlers, these classes handle receiving messages and dealing with them.
 */
public interface IModMessageHandler
{
	public void receiveMessage(String modidOfSender, IModMessage message);
}
