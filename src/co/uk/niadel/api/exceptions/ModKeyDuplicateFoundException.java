package co.uk.niadel.api.exceptions;


/**
 * Thrown if a duplicate mod is found. This should ideally never be thrown, because of the
 * naming convention you <i>are</i> following, right?
 * @author Niadel
 *
 */
public final class ModKeyDuplicateFoundException extends Throwable
{
	public ModKeyDuplicateFoundException()
	{
		super("Found duplicate mod key!");
	}
}
