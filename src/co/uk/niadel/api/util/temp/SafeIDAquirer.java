package co.uk.niadel.api.util.temp;

import java.util.Collection;
import java.util.Set;
import java.util.Random;

import co.uk.niadel.api.exceptions.FreeIdNotFoundException;

/**
 * Will be removed in the 1.8 update! This was put together in a matter of minutes, and as 1.8
 * will be out soon at the time of writing, this is very likely to be broken. Only use this if
 * you want your mod to be used in a mod pack.
 * 
 * @author Niadel
 */
public final class SafeIDAquirer 
{
	private static Set occupiedBlockIDs;
	private static Set occupiedItemIDs;
	
	private static final Random random = new Random();
	
	/**
	 * Keeps on trying to find a free id until it does. If it can't find a free id, well...
	 * You're screwed.
	 * 
	 * @param minId
	 * @param maxId
	 * @return triedId
	 * @throws FreeIdNotFoundException 
	 */
	@SuppressWarnings("null")
	public static int getFreeBlockID(int minId, int maxId) throws FreeIdNotFoundException
	{
		int triedId = random.nextInt(maxId - minId);
		boolean hasIDBeenFound = false;
		Collection<Integer> possibleIds = null;
		
		for (int i = minId; i == maxId; i++)
		{
			possibleIds.add(i);
		}
		
		if (!occupiedBlockIDs.containsAll(possibleIds))
		{
			while (!hasIDBeenFound)
			{
				if (!isIdOccupied(triedId, occupiedBlockIDs))
				{
					occupiedBlockIDs.add(triedId + 175 + minId);
					return triedId + 175 + minId;
				}
				else
				{
					triedId = random.nextInt(maxId - minId);
				}
			}
		}
		
		throw new FreeIdNotFoundException();
	}
	
	@SuppressWarnings("null")
	public static int getFreeItemID(int minId, int maxId) throws FreeIdNotFoundException
	{
		int triedId = random.nextInt(maxId - minId);
		boolean hasIDBeenFound = false;
		Collection<Integer> possibleIds = null;
		
		for (int i = minId; i == maxId; i++)
		{
			possibleIds.add(i);
		}
		
		if (!occupiedItemIDs.containsAll(possibleIds) | occupiedItemIDs.isEmpty())
		{
			while (!hasIDBeenFound)
			{
				if (!isIdOccupied(triedId, occupiedItemIDs))
				{
					occupiedItemIDs.add(triedId + 175 + minId);
					return triedId + 175 + minId;
				}
				else
				{
					triedId = random.nextInt(maxId - minId);
				}
			}
		}
		
		throw new FreeIdNotFoundException();
	}
	
	private static final boolean isIdOccupied(int id, Set setToCheck)
	{
		return setToCheck.contains(id);
	}
}
