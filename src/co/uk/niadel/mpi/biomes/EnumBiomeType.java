package co.uk.niadel.mpi.biomes;

import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;

/**
 * Only really used by BiomeRegistry, but is required to register any biome.
 * @author Niadel
 *
 */
public enum EnumBiomeType 
{
	HOT(0),
	TEMPERATE(1),
	CHILLY(2),
	COLD(3),
	ALL(4);
	
	/**
	 * The value of the type, used in the biome registry.
	 */
	@Internal
	private int value;

	EnumBiomeType(int number)
	{
		this.value = number;
	}

	/**
	 * Gets the internal value used by BiomeRegistry.
	 * @return The internal value used by BiomeRegistry.
	 */
	public int getValue()
	{
		return this.value;
	}
}
