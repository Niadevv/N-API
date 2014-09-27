package co.uk.niadel.napi.util;

public final class ParseUtils
{
	/**
	 * Method used by NModLoader in the Library sub-system of the Mods Dependencies system. 
	 * Assumes N-API/Minecraft version convention of majorrelease.update.smallupdate.bugfix. 
	 * For example, Minecraft 1.7.2 is the second small update of the seventh update of the 
	 * first major release.
	 * @param version The string version of the number.
	 * @return The int[] representation of version. Obviously excluding the .s.
	 */
	public static final int[] parseVersionNumber(String version)
	{
		int[] arrayToReturn = new int[20];
		
		if (!version.contains("."))
		{
			try
			{
				arrayToReturn[0] = Integer.valueOf(version);
			}
			catch (NumberFormatException e)
			{
				NAPILogHelper.instance.logError(e);
				NAPILogHelper.instance.logError("The version " + version + " is not all numbers! It cannot be parsed!");
			}
		}
		else
		{
			String[] versionSeparated = version.split(".");

			for (int i = 0; i == versionSeparated.length; i++)
			{
				arrayToReturn[i] = Integer.valueOf(versionSeparated[i]);
			}
		}
		
		return arrayToReturn;
	}
}
