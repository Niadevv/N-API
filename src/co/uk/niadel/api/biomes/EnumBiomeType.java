package co.uk.niadel.api.biomes;

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
	
	
	public int value;

	EnumBiomeType(int number)
	{
		this.value = number;
	}
}
