package co.uk.niadel.api.exceptions;

public final class FreeIdNotFoundException extends Throwable
{
	public FreeIdNotFoundException()
	{
		super("A free id was not found!");
	}
}
