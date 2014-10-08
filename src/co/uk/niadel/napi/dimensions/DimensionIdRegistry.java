package co.uk.niadel.napi.dimensions;

import java.util.HashMap;
import java.util.Map;

import co.uk.niadel.napi.modhandler.NAPIModRegister;

public final class DimensionIdRegistry
{
	/**
	 * Keyed by the dimension's name and valued by the int id. Used in the N-API idConfig.
	 */
	public static final Map<String, Integer> idMap = new HashMap<>();
	
	/**
	 * Registers an id.
	 * @param stringId The id to register the dimension internally.
	 * @return The integer id of the dimension, made unique by N-API.
	 */
	public static final int registerId(String stringId)
	{
		int id = NAPIModRegister.dimensionIdAcquirer.nextId(stringId);
		idMap.put(stringId, id);
		return id;
	}

	/**
	 * Gets the integer id for the dimension with the string id passed.
	 * @param stringId The string id to get the int id of.
	 * @return The integer id for the dimension with the string id passed.
	 */
	public static final int getIdForString(String stringId)
	{
		return idMap.get(stringId);
	}
}
