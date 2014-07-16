package co.uk.niadel.mpi.exceptions;

public final class OutdatedLibraryException extends Throwable
{
	public OutdatedLibraryException(String libraryId)
	{
		super("The library " + libraryId + " is outdated!");
	}
}
