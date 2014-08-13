package co.uk.niadel.mpi.config;

import java.util.Iterator;
import java.util.Map.Entry;

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
		if (!doesConfigValueExist(stringId))
		{
			addConfigValue(stringId, String.valueOf(id));
		}
	}
	
	/**
	 * Gets the int id of the specified alphanumeric id.
	 * @param stringId
	 * @return
	 */
	public int getId(String stringId)
	{
		return Integer.valueOf(getConfigValue(stringId));
	}
	
	/**
	 * Gets whether or not the specified id exists.
	 * @param stringId
	 * @return
	 */
	public boolean doesIdExist(String stringId)
	{
		return doesConfigValueExist(stringId);
	}
	
	/**
	 * Does the same as above, but checks the numeric id instead.
	 * @param numId
	 * @return
	 */
	public boolean doesIdExist(int numId)
	{
		Iterator<Entry<String, String>> dataIterator = this.data.entrySet().iterator();
		
		while (dataIterator.hasNext())
		{
			int currValue = Integer.valueOf(dataIterator.next().getValue());
			
			if (currValue == numId)
			{
				return true;
			}
		}
		
		return false;
	}
}
