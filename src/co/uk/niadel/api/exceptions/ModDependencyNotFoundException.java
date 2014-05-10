package co.uk.niadel.api.exceptions;

public class ModDependencyNotFoundException extends Throwable
{
	public ModDependencyNotFoundException(String modId)
	{
		super("One of the mod " + modId + "'s Dependencies have not been found!");
	}
}
