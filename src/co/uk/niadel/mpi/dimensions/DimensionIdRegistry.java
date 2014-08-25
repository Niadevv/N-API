package co.uk.niadel.mpi.dimensions;

import java.util.HashMap;
import java.util.Map;

import co.uk.niadel.mpi.modhandler.NAPIModRegister;

public final class DimensionIdRegistry
{
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
		int id = NAPIModRegister.dimensionIdAcquirer.nextId(stringId);
		idMap.put(stringId, id);
		return id;
	}

	public static final int getIdForString(String stringId)
	{
		return idMap.get(stringId);
	}
}
