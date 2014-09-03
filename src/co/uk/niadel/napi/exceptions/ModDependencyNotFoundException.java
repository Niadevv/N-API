package co.uk.niadel.napi.exceptions;

public final class ModDependencyNotFoundException extends Throwable
{
	public ModDependencyNotFoundException(String modId)
	{
		super("One of the mod " + modId + "'s Dependencies have not been found!");
	}
}
