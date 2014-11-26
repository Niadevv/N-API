package co.uk.niadel.napi.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Is in charge of logging things. It should log to both an output file and to the Development Console.
 * @author Niadel
 *
 */
public final class NAPILogHelper
{
	/**
	 * The logger, this allows N-API to log things to the Development Console.
	 */
	public static Logger logger = LogManager.getLogger("N-API");

	public static NAPILogHelper instance = new NAPILogHelper();

	public static void log(Level logLevel, Object logged)
	{
		logger.log(logLevel, logged);
	}
	
	/**
	 * Logs a standard message.
	 * @param log The object to log.
	 */
	public static void log(Object log)
	{	
		logger.log(Level.INFO, log);
	}

	/**
	 * Logs an error.
	 * @param error The error object to log.
	 */
	public static void logError(Object error)
	{	
		logger.error(error);
	}

    /**
     * Logs a critical error.
     * @param critical The critical object to log.
     */
    public static void logCritical(Object critical)
    {
        logger.error(critical);
    }
	
	/**
	 * Logs a warning.
	 * @param warning The critical warning to log.
	 */
	public static void logWarn(Object warning)
	{	
		logger.warn(warning);
	}
	
	/**
	 * Logs a message for debugging.
	 * @param debugMessage The debug message to log.
	 */
	public static void logDebug(Object debugMessage)
	{
		logger.debug(debugMessage);
	}
}
