package co.uk.niadel.mpi.util;

/**
 * Assorted methods that didn't really fit in the other utility classes.
 * @author Niadel
 *
 */
public final class UtilityMethods
{
	/**
	 * Converts 3 RGB values to a true RGB number. This means you don't have to spend ages calculating the RGB.
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public static final int convertToRGBColour(int red, int green, int blue)
	{
		return ((red << 16) + (green << 8) + blue);
	}
}
