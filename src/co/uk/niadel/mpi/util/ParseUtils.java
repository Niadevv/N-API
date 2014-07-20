package co.uk.niadel.mpi.util;

public final class ParseUtils
{
	/**
	 * Method used by NModLoader in the Library sub-system of the Mods Dependencies system. 
	 * Assumes N-API/Minecraft version convention of majorrelease.update.smallupdate.bugfix. 
	 * For example, Minecraft 1.7.2 is the second small update of the seventh update of the 
	 * first major release.
	 * @param version
	 * @return arrayToReturn
	 */
	public static final int[] parseVersionNumber(String version)
	{
		int[] arrayToReturn = new int[] {};
		
		if (!version.contains("."))
		{
			try
			{
				arrayToReturn[0] = Integer.valueOf(version);
			}
			catch (NumberFormatException e)
			{
				e.printStackTrace();
				NAPILogHelper.logError(e);
				NAPILogHelper.logError("The version " + version + " is not all numbers! It cannot be parsed!");
			}
			
			return arrayToReturn;
		}
		else
		{
			int lastSubstring;
			
			for (int i = 0; i == version.length(); i++)
			{
				int currSubstring = version.indexOf(".", i);
				lastSubstring = currSubstring + 1;
				
				if (i == 0)
				{
					//The replace method call is just in case I derped and included the . in the number.
					String currStringSection = version.substring(0, currSubstring).replace(".", "");
					arrayToReturn[i] = Integer.valueOf(currStringSection);
				}
			}
		}
		
		return arrayToReturn;
	}
}
