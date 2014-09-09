package co.uk.niadel.napi.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Is in charge of logging things. It should log to both an output file and to the Development Console.
 * @author Niadel
 *
 */
public class NAPILogHelper extends co.uk.niadel.commons.logging.Logger
{
	/**
	 * The logger, this allows N-API to log things to the Development Console.
	 */
	public static Logger logger = LogManager.getLogger("N-API");

	public static NAPILogHelper instance = new NAPILogHelper();

	private NAPILogHelper()
	{
		super("N-API");
	}

	public static void log(Level logLevel, Object logged)
	{
		logger.log(logLevel, logged);
	}
	
	/**
	 * Logs a standard message.
	 * @param log The object to log.
	 */
	public void log(Object log)
	{	
		log(Level.INFO, log);
	}

	/**
	 * Logs an error.
	 * @param error The error object to log.
	 */
	public void logError(Object error)
	{	
		log(Level.ERROR, error);
	}

    /**
     * Logs a critical error.
     * @param critical The critical object to log.
     */
    public void logCritical(Object critical)
    {
        log(Level.FATAL, critical);
    }
	
	/**
	 * Logs a warning.
	 * @param warning The critical warning to log.
	 */
	public void logWarn(Object warning)
	{	
		log(Level.WARN, warning);
	}
	
	/**
	 * Logs a message for debugging.
	 * @param debugMessage The debug message to log.
	 */
	public final void logDebug(Object debugMessage)
	{
		log(Level.DEBUG, debugMessage);
	}
}
