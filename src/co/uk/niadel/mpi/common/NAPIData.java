package co.uk.niadel.mpi.common;

/**
 * Used to get info about the running version of N-API. Versioning follows Minecraft's
 * versioning conventions: majorversion.minorversion.bugfixversion
 * With the versions all capable of going up as high as possible, and don't follow decimal
 * logic.
 * @author Niadel
 *
 */
public class NAPIData
{
	/**
	 * Gets the N-API name.
	 * @return
	 */
	public static final String getName()
	{
		return "N-API";
	}
	
	/**
	 * Gets the full version of N-API.
	 * @return
	 */
	public static final String getFullVersion()
	{
		return getState() + " " + getName() + "-" + getMajorVersion() + "." + getMinorVersion() + "." + getBugFixVersion();
	}
	
	/**
	 * Gets whether or not N-API is beta or otherwise.
	 */
	public static final String getState()
	{
		return "early-alpha";
	}
	
	/**
	 * Gets the major version. 2 would effectively mean a complete overhaul.
	 * @return
	 */
	public static final String getMajorVersion()
	{
		return "1";
	}
	
	/**
	 * Gets the minor version, should increment by one every time a new feature is released
	 * or Minecraft updates.
	 * @return
	 */
	public static final String getMinorVersion()
	{
		return "0";
	}
	
	/**
	 * Gets the bug fix version. Should increment everytime a bug fix release is released.
	 * @return
	 */
	public static final String getBugFixVersion()
	{
		return "0";
	}
}
