package co.uk.niadel.mpi.init;

import co.uk.niadel.mpi.common.NAPIData;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.mpi.util.NAPILogHelper;
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
	/**
	 * The minimum allowed version of java for N-API to run.
	 */
	public static final double REQ_JAVA_VERSION = 1.7;

	/**
	 * The minimum allowed version of java for N-API to run, as a String and only one number.
	 */
	public static final String REQ_JAVA_VERSION_STRING = "7";

	public static void main(String[] args)
	{
		if (checkJavaVersion())
		{
			Main.main(prepArgs(args));
		}
		else
		{
			NAPILogHelper.logCritical("JAVA VERSION IS NOT " + REQ_JAVA_VERSION_STRING + "! N-API WILL NOT RUN! If you wish to run N-API, use Java " + REQ_JAVA_VERSION_STRING + ".");
		}
	}

	/**
	 * Prepares the args for launching, adding important arguments if necessary.
	 * @param args The args to prepare.
	 */
	public static String[] prepArgs(String[] args)
	{
		List<String> argList = new ArrayList<>();

		argList.addAll(Arrays.asList(args));

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

		if (!argList.contains("--username"))
		{
			argList.add("--username");
			argList.add("N-APIDev");
		}

		return argList.toArray(new String[argList.size()]);
	}

	/**
	 * Checks the java version to see if it is appropriate for launch.
	 * @return Whether or not the java version is appropriate for launch.
	 */
	public static final boolean checkJavaVersion()
	{
		if (JavaChecker.isJavaVersionOrGreater(REQ_JAVA_VERSION))
		{
			NAPILogHelper.log("Java version is appropriate!");
			return true;
		}
		else
		{
			NAPILogHelper.logCritical("Java version is not " + REQ_JAVA_VERSION_STRING + "! If you are using Java 6, for the love of god, update all ready!");
			return false;
		}
	}
}
