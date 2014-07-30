package co.uk.niadel.mpi.dimensions;

import java.util.HashMap;
import java.util.Map;

import co.uk.niadel.mpi.modhandler.ModRegister;
import co.uk.niadel.mpi.util.NAPILogHelper;
import net.minecraft.world.WorldProvider;

/**
 * Part of dimension registering, this is where you add World Providers.
 * @author Niadel
 *
 */
public final class WorldProviderRegistry
{
	/**
	 * Map of the providers.
	 */
	public static Map<Integer, WorldProvider> providerMap = new HashMap<>();

	/**
	 * Adds a provider, getting the int id from the string value provided.
	 * @param dimensionId
	 * @param provider
	 */
	public static final void addProvider(String dimensionId, WorldProvider provider)
	{
		providerMap.put(ModRegister.dimensionIdAcquirer.nextId(dimensionId), provider);
	}
	
	/**
	 * Returns a provider with the specified id.
	 * @param id
	 * @return
	 */
	public static final WorldProvider getProvider(int id)
	{
		return providerMap.get(id);
	}
	
	/**
	 * Gets whether or not the provider with the specified id exists.
	 * @param id
	 * @return
	 */
	public static final boolean doesProviderExist(int id)
	{
		return getProvider(id) != null;
	}
}
