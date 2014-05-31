package co.uk.niadel.mpi.exceptions;

public class MCreatorDetectedException extends Throwable
{
	public MCreatorDetectedException(String modId)
	{
		super("The mod " + modId + " was made with MCreator. Please tell it's creator to go learn java or stop 'modding'.");
	}
}
