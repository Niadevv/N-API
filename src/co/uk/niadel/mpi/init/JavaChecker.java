package co.uk.niadel.mpi.init;

/**
 * Used for checking the version of Java that the player uses.
 */
public class JavaChecker
{
	public static final boolean isJavaVersionOrGreater(double minVersion)
	{
		double currentJavaVersion = Double.valueOf(Runtime.class.getPackage().getImplementationVersion().substring(0, 3));

		return currentJavaVersion >= minVersion;
	}
}
