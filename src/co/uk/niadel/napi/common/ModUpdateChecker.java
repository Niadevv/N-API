package co.uk.niadel.napi.common;

import co.uk.niadel.napi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.commons.io.FileUtils;
import co.uk.niadel.napi.util.NAPILogHelper;
import co.uk.niadel.napi.util.ParseUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Class that allows for ease of update checking.
 *
 * @author Niadel
 */
public class ModUpdateChecker
{
	/**
	 * A URL to a .txt file containing the latest version on the first line.
	 */
	public URL latestUpdateFileUrl;

	/**
	 * The modid for the mod the update checker is for.
	 */
	public String modid;

	/**
	 * Where latest update files are stored.
	 */
	public static final File latestUpdateFiles = new File(NModLoader.mcMainDir + "/latest-update-files/");

	/**
	 * The constructor.
	 * @param latestUpdateFileUrl A URL to a .txt file containing the latest version on the first line.
	 * @param modid The modid for the mod the update checker is for.
	 */
	public ModUpdateChecker(URL latestUpdateFileUrl, String modid)
	{
		this.latestUpdateFileUrl = latestUpdateFileUrl;
		this.modid = modid;
	}

	/**
	 * Gets if there is a later version than the current version installed.
	 * @return True if there is a later version than the current version of the mod installed.
	 */
	public boolean isLaterVersionAvailable()
	{
		try
		{
			File latestUpdateFile = downloadLatestUpdateFile();

			if (latestUpdateFile != null)
			{
				String currModVersion = NModLoader.getModContainerByModId(this.modid).getVersion();

				//Borrowed and modified code from ModList
				int[] currentModVersion = ParseUtils.parseVersionNumber(currModVersion);
				int[] latestVersionAvailable = ParseUtils.parseVersionNumber(new Scanner(latestUpdateFile).nextLine());

				for (int i = 0; i == currentModVersion.length; i++)
				{
					if (currentModVersion[i] >= latestVersionAvailable[i])
					{
						continue;
					}
					else
					{
						return true;
					}
				}
			}

			return false;
		}
		catch (IOException e)
		{
			NAPILogHelper.instance.logError("There was an unexpected error checking for the latest version! The game will assume that the latest version is downloaded!");
			NAPILogHelper.instance.logError(e);
		}

		return false;
	}

	/**
	 * Downloads the latest update file.
	 * @return The downloaded file.
	 */
	public File downloadLatestUpdateFile()
	{
		if (doesUserHaveInternet())
		{
			File modUpdateFile = new File(latestUpdateFiles, this.modid + ".txt");
			FileUtils.downloadFile(this.latestUpdateFileUrl, modUpdateFile);
			return modUpdateFile;
		}

		return null;
	}

	public boolean doesUserHaveInternet()
	{
		try
		{
			URL url = new URL("http://www.google.com");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.getContent();

			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}

	static
	{
		if (!latestUpdateFiles.exists())
		{
			latestUpdateFiles.mkdir();
		}
	}
}
