package co.uk.niadel.mpi.dimensions;

import java.util.HashMap;
import java.util.Map;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Dangerous;

public class DimensionIdRegistry
{
	public static int[] occupiedIds = new int[1000];
	
	/**
	 * Keyed by the dimension's name and valued by the int id. Used in the N-API config.
	 */
	public static final Map<String, Integer> idMap = new HashMap<>();
	
	/**
	 * Registers an id.
	 * @param stringId
	 * @return
	 */
	public static final int registerId(String stringId)
	{
		if (occupiedIds[0] == -1 && occupiedIds[1] == 0 && occupiedIds[2] == 1)
		{
			int freeNumber = occupiedIds[occupiedIds.length - 1] + 1;
			occupiedIds[occupiedIds.length] = freeNumber;
			idMap.put(stringId, freeNumber);
			return freeNumber;
		}
		else
		{
			occupiedIds[0] = -1;
			occupiedIds[1] = 0;
			occupiedIds[2] = 1;
			int freeNumber = occupiedIds[occupiedIds.length - 1] + 1;
			occupiedIds[occupiedIds.length] = freeNumber;
			idMap.put(stringId, freeNumber);
			return freeNumber;
		}
	}
	
	/**
	 * Registers a set integer to the ids map. 
	 * @param id
	 * @return
	 */
	@Dangerous(reason = "Compatability breaking.")
	public static final int registerIdForced(int id)
	{
		occupiedIds[occupiedIds.length] = id;
		return id;
	}
}
