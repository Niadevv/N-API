package co.uk.niadel.mpi.exceptions;

public final class FreeIdUnacquirableException extends Throwable
{
	public FreeIdUnacquirableException()
	{
		super("A free id was not found!");
	}
}
