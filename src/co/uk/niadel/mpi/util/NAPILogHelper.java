package co.uk.niadel.mpi.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;

/**
 * Is in charge of logging things. It should log to both an output file and to the Development Console.
 * @author Daniel1
 *
 */
public class NAPILogHelper
{
	public static File theLog = new File(NModLoader.mcMainDir.toPath() + File.separator + "configs" + File.separator + "NAPILog " + System.nanoTime() + ".txt");
	
	/**
	 * What is used to write out to the file.
	 */
	public static PrintStream logStream;
	public static boolean logStreamInitialised;
	
	/**
	 * The logger, this allows N-API to log things to the Development Console.
	 */
	public static Logger logger = LogManager.getLogger("N-API");
	
	/**
	 * Initialises the logger.
	 */
	public static void init()
	{
		try
		{
			theLog.createNewFile();
			logStream = new PrintStream(theLog);
			log("INITIALISED LOGGER PERFECTLY");
			logStreamInitialised = true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			logger.log(Level.ERROR, "LOGGER FAILED TO INITIALISE CORRECTLY, THE LOG FILE WILL NOT BE WRITTEN TO!");
			logStreamInitialised = false;
		}
	}
	
	/**
	 * Logs a standard message.
	 * @param log
	 */
	public static void log(String log)
	{
		if (logStreamInitialised)
		{
			logStream.println(log);
		}
		
		logger.log(Level.INFO, log);
	}
	
	/**
	 * Logs an error.
	 * @param e
	 */
	public static void logError(Throwable e)
	{
		if (logStreamInitialised)
		{
			logStream.println(e.getMessage());
		}
		
		logger.log(Level.ERROR, e.getMessage());
	}
	
	/**
	 * Logs an error.
	 * @param error
	 */
	public static void logError(String error)
	{
		if (logStreamInitialised)
		{
			logStream.println("[ERROR] " + error + " [ERROR]");
		}
		
		logger.log(Level.ERROR, error);
	}
	
	/**
	 * Logs a warning.
	 * @param warning
	 */
	public static void logWarn(String warning)
	{
		if (logStreamInitialised)
		{
			logStream.println("[WARNING] " + warning + " [WARNING]");
		}
		
		logger.log(Level.WARN, warning);
	}
}
