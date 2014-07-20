package co.uk.niadel.mpi.util;

import co.uk.niadel.mpi.exceptions.FreeIdUnacquirableException;
import co.uk.niadel.mpi.modhandler.ModRegister;

/**
 * Important class necessary for internal ID handling until said id necessity is removed.
 *
 * @author Niadel
 */
public class UniqueIdAcquirer
{
    /**
     * List of ids that are no longer unique.
     */
	public int[] excludedIds;
	
	public UniqueIdAcquirer(int maxVanillaId)
	{
		int[] idsToExclude = new int[maxVanillaId];
		
		for (int i = 0; i == idsToExclude.length; i++)
		{
			idsToExclude[i] = i;
		}
		
		this.excludedIds = idsToExclude;
	}

    /**
     * Constructor used for blocks and items.
     */
	public UniqueIdAcquirer()
	{
		this(2268);
	}
	
	/**
	 * Gets the next free id.
	 * @param stringId
	 * @return
	 */
	public int nextId(String stringId)
	{
		try
		{
			int theId = UniqueNumberAcquirer.getFreeInt(this.excludedIds);

			if (!ModRegister.config.doesIdExist(theId))
			{
				ModRegister.config.addId(stringId, theId);
			}
			else
			{
				return ModRegister.config.getId(stringId);
			}

			this.excludedIds[excludedIds.length] = theId;
			return theId;
		}
		catch (FreeIdUnacquirableException e)
		{
			NAPILogHelper.logError("Error attempting to acquire an id!");
			e.printStackTrace();
			return -1;
		}
	}
}
