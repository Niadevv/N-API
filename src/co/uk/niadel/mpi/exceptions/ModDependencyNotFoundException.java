package co.uk.niadel.mpi.exceptions;

public class ModDependencyNotFoundException extends Throwable
{
	public ModDependencyNotFoundException(String modId)
	{
		super("One of the mod " + modId + "'s Dependencies have not been found!");
	}
}
