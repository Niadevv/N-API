package co.uk.niadel.mpi.common;

/**
 * Used to get info about the running version of N-API. Versioning follows Minecraft's
 * versioning conventions: majorversion.minorversion.bugfixversion
 * With the versions all capable of going up as high as possible, and don't follow decimal
 * logic.
 * @author Niadel
 *
 */
public final class NAPIData
{
	/**
	 * N-API's name.
	 */
	public static final String NAME = "N-API";
	
	/**
	 * N-API's mod id.
	 */
	public static final String MODID = "NIADEL_n_api";
	
	/**
	 * N-API's major version, increments every major overhaul after release.
	 */
	public static final String MAJOR_VERSION = "1";
	
	/**
	 * N-API's minor version, increments every update that just adds new features.
	 */
	public static final String MINOR_VERSION = "0";
	
	/**
	 * N-API's bug fix version, increments every time I fix a "Nia-Derp" or other bug.
	 */
	public static final String BUGFIX_VERSION = "0";
	
	/**
	 * The purely numeric version of N-API. Only used in ModRegister.
	 */
	public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + BUGFIX_VERSION;
	
	/**
	 * The version of Minecraft N-API is for.
	 */
	public static final String MC_VERSION = "1.7.10";
	
	/**
	 * Changes depending on what state of development this version is for. Values I plan it to be: 
	 * alpha, beta, release, snapshot-[whateversnapshotnumberitisfollowingMCsnapshotnamingconventions],
	 * alpha-snapshot-[snapshot], beta-snapshot-[snapshot]
	 */
	public static final String STATE = "alpha";
	
	/**
	 * The full version of N-API.
	 */
	public static final String FULL_VERSION = NAME + "-" + MC_VERSION + "-" + STATE + "-" + VERSION;
	
	/**
	 * Gets the version of Minecraft N-API is for.
	 * @return
	 */
	public static final String getMCVersion()
	{
		return "1.7.10";
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
	 * or Minecraft updates after release to public.
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
	
	/**
	 * Gets the version of N-API without the name and state prefix.
	 * @return
	 */
	public static final String getNumericVersion()
	{
		return getMajorVersion() + "." + getMinorVersion() + "." + getBugFixVersion();
	}
	
	public static final String getModId()
	{
		return "NIADEL_n_api";
	}
}
