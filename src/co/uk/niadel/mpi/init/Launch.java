package co.uk.niadel.mpi.init;

import co.uk.niadel.mpi.common.NAPIData;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.mpi.util.ArrayUtils;
import net.minecraft.client.main.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used to launch N-API with special initialisations and stuff. Significantly better than the default MCP Start.java as it adds the 1.7.10
 * added args.
 */
public class Launch
{
	public static void main(String[] args)
	{
		Main.main(prepArgs(args));
	}

	/**
	 * Prepares the args for launching, adding important arguments if necessary.
	 * @param args The args to prepare.
	 */
	public static String[] prepArgs(String[] args)
	{
		List<String> argList = new ArrayList<>();

		for (String arg : args)
		{
			argList.add(arg);
		}

		if (!argList.contains("--accessToken"))
		{
			argList.add("--accessToken");
			argList.add("N-API_ACCESS_TOKEN");
		}

		if (!argList.containsAll(Arrays.asList("--version", NAPIData.MC_VERSION)))
		{
			argList.add("--version");
			argList.add(NAPIData.MC_VERSION);
		}

		if (!argList.contains("--userProperties={}"))
		{
			argList.add("--userProperties={}");
		}

		if (!argList.containsAll(Arrays.asList("--assetIndex", NAPIData.MC_VERSION)))
		{
			argList.add("--assetIndex");
			argList.add(NAPIData.MC_VERSION);
		}

		return argList.toArray(new String[0]);
	}
}
