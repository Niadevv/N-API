package co.uk.niadel.napi.client.resources;

import co.uk.niadel.napi.util.NAPILogHelper;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Used to allow people to use the assets system like in FML.
 */
public class ModAssetsHandler
{
	public static final void scanForAssets()
	{
		try
		{
			URL assetsFolderUrl = ModAssetsHandler.class.getResource("assets/");
			File assetsFolder = new File(assetsFolderUrl.toURI());

			if (assetsFolder.isDirectory())
			{
				for (String child : assetsFolder.list())
				{
					if (!(child == "realms") && !(child == "minecraft"))
					{
						ResourcesRegistry.addResourceDomain(child);
					}
				}
			}
			else
			{
				NAPILogHelper.instance.logError("Cannot find assets folder!");
			}
		}
		catch (URISyntaxException e)
		{
			NAPILogHelper.instance.logError(e);
		}
	}
}
