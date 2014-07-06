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
 * @author Niadel
 *
 */
public class NAPILogHelper
{
	/**
	 * The logger, this allows N-API to log things to the Development Console.
	 */
	public static Logger logger = LogManager.getLogger("N-API");
	
	/**
	 * Logs a standard message.
	 * @param log
	 */
	public static void log(String log)
	{	
		logger.log(Level.INFO, log);
	}
	
	/**
	 * Logs an error.
	 * @param e
	 */
	public static void logError(Throwable e)
	{	
		logger.log(Level.ERROR, e.getMessage());
	}
	
	/**
	 * Logs an error.
	 * @param error
	 */
	public static void logError(String error)
	{	
		logger.log(Level.ERROR, error);
	}
	
	/**
	 * Logs a warning.
	 * @param warning
	 */
	public static void logWarn(String warning)
	{	
		logger.log(Level.WARN, warning);
	}
	
	/**
	 * Logs a message for debugging.
	 * @param debugMessage
	 */
	public static final void logDebug(String debugMessage)
	{
		logger.log(Level.DEBUG, debugMessage);
	}
}
