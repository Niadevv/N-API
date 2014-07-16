package co.uk.niadel.mpi.util;

import co.uk.niadel.mpi.modhandler.ModRegister;

public class UniqueIdAcquirer
{
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
}
