package co.uk.niadel.mpi.config;

/**
 * Used for handling numeric ids like dimension ids and, up to 1.8, block and item ids.
 */
public class IdConfiguration extends Configuration
{
	public IdConfiguration(String configName)
	{
		super(configName);
	}
	
	/**
	 * Adds an id to the config.
	 * @param stringId
	 * @param id
	 */
	public void addId(String stringId, int id)
	{
		if (!doesOptionExist(stringId))
		{
			addOption(stringId, String.valueOf(id));
		}
	}
	
	/**
	 * Gets the int id of the specified alphanumeric id.
	 * @param stringId
	 * @return
	 */
	public int getId(String stringId)
	{
		return Integer.valueOf(getOptionValue(stringId));
	}
}
