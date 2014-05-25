package co.uk.niadel.api.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import co.uk.niadel.api.modhandler.loadhandler.NModLoader;

public class NAPILogHelper
{
	public static File theLog = new File(NModLoader.mcMainDir.toPath() + File.separator + "configs" + File.separator + "NAPILog " + System.nanoTime() + ".txt");
	public static PrintStream logStream;
	
	public static void init()
	{
		try
		{
			theLog.createNewFile();
			logStream = new PrintStream(theLog);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void log(String log)
	{
		logStream.println(log);
	}
	
	public static void logError(Throwable e)
	{
		logStream.println(e.getMessage());
	}
	
	public static void logError(String error)
	{
		logStream.println("[ERROR] " + error + " [ERROR]");
	}
	
	public static void logWarn(String warning)
	{
		logStream.println("[WARNING] " + warning + " [WARNING]");
	}
}
