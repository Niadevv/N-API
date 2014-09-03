package co.uk.niadel.napi.exceptions;

public final class FreeIdUnacquirableException extends Throwable
{
	public FreeIdUnacquirableException()
	{
		super("A free id was not found!");
	}
}
