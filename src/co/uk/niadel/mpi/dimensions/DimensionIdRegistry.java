package co.uk.niadel.mpi.dimensions;

import java.util.HashMap;
import java.util.Map;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Dangerous;

public class DimensionIdRegistry
{
	public static int[] occupiedIds = new int[] {-1, 0, 1};
	
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
		int freeNumber = occupiedIds[occupiedIds.length - 1] + 1;
		occupiedIds[occupiedIds.length] = freeNumber;
		idMap.put(stringId, freeNumber);
		return freeNumber;
	}
	
	@Dangerous(reason = "Compatability breaking.")
	public static final int registerIdForced(int id)
	{
		occupiedIds[occupiedIds.length] = id;
		return id;
	}
}
