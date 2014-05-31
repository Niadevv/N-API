package co.uk.niadel.mpi.exceptions;

public final class FreeIdNotFoundException extends Throwable
{
	public FreeIdNotFoundException()
	{
		super("A free id was not found!");
	}
}
