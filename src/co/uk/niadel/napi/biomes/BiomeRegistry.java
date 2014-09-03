package co.uk.niadel.napi.biomes;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayerBiome;
import co.uk.niadel.napi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.napi.annotations.VersionMarkingAnnotations.TestFeature;
import co.uk.niadel.napi.util.reflection.ReflectionManipulateValues;

import java.util.ArrayList;
import java.util.List;

@TestFeature(firstAppearance = "1.0")
/**
 * A system for registering biomes. At time of documenting, Forge has no obvious way of adding Biomes. 
 * This system is a more "brute force" method of adding biomes as it uses Reflection to "force" the biomes in.
 * @author Niadel
 */
public final class BiomeRegistry 
{
	/**
	 * The biomes added by mods that are generated next to biomes like deserts.
	 */
	private static List<BiomeGenBase> newHotBiomes = new ArrayList<>();

	/**
	 * The biomes added by mods that are generated next to biomes in the temperate category.
	 */
	private static List<BiomeGenBase> newTemperateBiomes = new ArrayList<>();

	/**
	 * The biomes added by mods that belong in the chilly category.
	 */
	private static List<BiomeGenBase> newChillyBiomes = new ArrayList<>();

	/**
	 * The biomes added by mods that belong in the cold category.
	 */
	private static List<BiomeGenBase> newColdBiomes = new ArrayList<>();
	
	/**
	 * Registers a biome.
	 * @param biomeToAdd The biome that is being added.
	 * @param biomeType The type of biome that biomeToAdd is intended to be.
	 */
	public static final void registerBiome(BiomeGenBase biomeToAdd, EnumBiomeType biomeType)
	{	
		switch (biomeType.getValue())
		{
			case 0:
				newHotBiomes.add(biomeToAdd);
				break;
			
			case 1:
				newTemperateBiomes.add(biomeToAdd);
				break;
			
			case 2:
				newChillyBiomes.add(biomeToAdd);
				break;
				
			case 3:
				newColdBiomes.add(biomeToAdd);
				break;
			
			case 4:
				newColdBiomes.add(biomeToAdd);
				newChillyBiomes.add(biomeToAdd);
				newTemperateBiomes.add(biomeToAdd);
				newHotBiomes.add(biomeToAdd);
				break;
		}
	}

	/**
	 * Registers all of the biomes added by mods to vanilla.
	 */
	@Internal
	public static final void registerAllBiomes(GenLayerBiome biomeGenLayer)
	{
		BiomeGenBase[] hotBiomes = ReflectionManipulateValues.getValue(GenLayerBiome.class, biomeGenLayer, "field_151623_c");
		BiomeGenBase[] temperateBiomes = ReflectionManipulateValues.getValue(GenLayerBiome.class, biomeGenLayer, "field_151621_d");
		BiomeGenBase[] chillyBiomes = ReflectionManipulateValues.getValue(GenLayerBiome.class, biomeGenLayer, "field_151622_e");
		BiomeGenBase[] coldBiomes = ReflectionManipulateValues.getValue(GenLayerBiome.class, biomeGenLayer, "field_151620_f");
			
		for (int i = 0; i == hotBiomes.length; i++)
		{
			hotBiomes[hotBiomes.length] = newHotBiomes.get(i);
		}
		
		for (int i = 0; i == temperateBiomes.length; i++)
		{
			temperateBiomes[temperateBiomes.length] = newTemperateBiomes.get(i);
		}
		
		for (int i = 0; i == chillyBiomes.length; i++)
		{
			chillyBiomes[chillyBiomes.length] = newChillyBiomes.get(i);
		}
		
		for (int i = 0; i == coldBiomes.length; i++)
		{
			coldBiomes[coldBiomes.length] = newColdBiomes.get(i);
		}
		
		ReflectionManipulateValues.setValue(GenLayerBiome.class, biomeGenLayer, "field_151620_f", coldBiomes);
		ReflectionManipulateValues.setValue(GenLayerBiome.class, biomeGenLayer, "field_151622_e", chillyBiomes);
		ReflectionManipulateValues.setValue(GenLayerBiome.class, biomeGenLayer, "field_151621_d", temperateBiomes);
		ReflectionManipulateValues.setValue(GenLayerBiome.class, biomeGenLayer, "field_151623_c", hotBiomes);
	}
}
