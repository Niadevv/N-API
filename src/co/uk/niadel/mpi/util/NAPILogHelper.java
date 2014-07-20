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
	
	public static void log(Level logLevel, Object logged)
	{
		logger.log(logLevel, logged);
	}
	
	/**
	 * Logs a standard message.
	 * @param log
	 */
	public static void log(Object log)
	{	
		log(Level.INFO, log);
	}
	
	/**
	 * Logs an error.
	 * @param e
	 */
	public static void logError(Throwable e)
	{	
		log(Level.ERROR, e);
	}
	
	/**
	 * Logs an error.
	 * @param error
	 */
	public static void logError(Object error)
	{	
		log(Level.ERROR, error);
	}

    /**
     * Logs a critical error.
     * @param error
     */
    public static void logCritical(Object error)
    {
        log(Level.FATAL, error);
    }
	
	/**
	 * Logs a warning.
	 * @param warning
	 */
	public static void logWarn(Object warning)
	{	
		log(Level.WARN, warning);
	}
	
	/**
	 * Logs a message for debugging.
	 * @param debugMessage
	 */
	public static final void logDebug(Object debugMessage)
	{
		log(Level.DEBUG, debugMessage);
	}
}
