package co.uk.niadel.napi.config;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Used for handling numeric ids like dimension ids and block and item ids.
 *
 * @author Niadel
 */
public class IdConfiguration extends Configuration
{
	public IdConfiguration(String configName)
	{
		super(configName);
	}
	
	/**
	 * Adds an id to the idConfig.
	 * @param stringId The string id representation of id.
	 * @param id The int id to add.
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
	 * @param stringId The string version of the numeric id.
	 * @return The integer id of stringId.
	 */
	public int getId(String stringId)
	{
		String intId = getConfigValue(stringId);

		if (intId != null)
		{
			return Integer.valueOf(getConfigValue(stringId));
		}
		else
		{
			return -1;
		}
	}
	
	/**
	 * Gets whether or not the specified id exists.
	 * @param stringId The id version of the int id to test.
	 * @return Whether or not there is an int id for stringId.
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
