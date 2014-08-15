package co.uk.niadel.mpi.dimensions;

import java.util.HashMap;
import java.util.Map;

import co.uk.niadel.mpi.modhandler.ModRegister;
import co.uk.niadel.mpi.util.NAPILogHelper;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldProviderSurface;

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
	public static Map<Integer, Class<? extends WorldProvider>> providerMap = new HashMap<>();

	/**
	 * Adds a provider, getting the int id from the string value provided.
	 * @param dimensionId
	 * @param provider
	 */
	public static final void addProvider(String dimensionId, Class<? extends WorldProvider> provider)
	{
		providerMap.put(ModRegister.dimensionIdAcquirer.nextId(dimensionId), provider);
	}

	private static final void addVanillaProvider(int id, Class<? extends WorldProvider> clazz)
	{
		providerMap.put(id, clazz);
	}
	
	/**
	 * Returns a provider with the specified id.
	 * @param id
	 * @return
	 */
	public static final WorldProvider getProvider(int id)
	{
		try
		{
			return providerMap.get(id).newInstance();
		}
		catch (IllegalAccessException | InstantiationException e)
		{
			e.printStackTrace();

			if (e instanceof IllegalAccessException)
			{
				NAPILogHelper.logError("If your provider has a constructor, make it public!");
			}
			else if (e instanceof InstantiationException)
			{
				NAPILogHelper.logError("Please make sure your provider is NOT abstract and that your constructor does not take any args!");
			}

			//Default to avoid nasty errors Minecraft side.
			return new WorldProviderSurface();
		}
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

	static
	{
		addVanillaProvider(-1, WorldProviderHell.class);
		addVanillaProvider(0, WorldProviderSurface.class);
		addVanillaProvider(1, WorldProviderEnd.class);
	}
}
