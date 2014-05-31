package co.uk.niadel.mpi.dimensions;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.WorldProvider;

/**
 * Part of dimension registering, this is where you add World Providers.
 * @author Niadel
 *
 */
public class WorldProviderRegistry
{
	/**
	 * Map of the providers.
	 */
	public static Map<Integer, WorldProvider> providerMap = new HashMap<>();
	
	/**
	 * Adds a provider.
	 * @param id
	 * @param provider
	 */
	public static final void addProvider(int id, WorldProvider provider)
	{
		if (!(id == -1) || !(id == 0) || !(id == 1))
		{
			providerMap.put(id, provider);
		}
		else
		{
			System.err.println("YOU CANNOT REGISTER A PROVIDER IN A VANILLA PROVIDER ID (-1, 0, or 1)!");
		}
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
		if (getProvider(id) != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
