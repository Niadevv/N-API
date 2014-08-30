package co.uk.niadel.mpi.common.modinteraction;

/**
 * A message sent by mods to a ModMessageNetwork/ModMessageChannel. Allows for sending custom data.
 * @author Niadel
 */
public interface IModMessage
{
	/**
	 * Gets the data you want to send.
	 * @return The data you want to send.
	 */
	public Object getMessage();
}
