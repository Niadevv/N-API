package co.uk.niadel.api.biomes;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiome;
import co.uk.niadel.api.annotations.VersionMarkingAnnotations.TestFeature;
import co.uk.niadel.api.util.reflection.ReflectionManipulateValues;

@TestFeature(firstAppearance = "1.0")
/**
 * A system for registering biomes. I'm unsure of if Forge allows you to add biomes to certain
 * temperature groups, but, hey. This system is a more "brute force" method of adding biomes
 * as it uses Reflection to force the biomes in.
 * @author Niadel
 */
public final class BiomeRegistry 
{
	static BiomeGenBase[] hotBiomes;
	static BiomeGenBase[] temperateBiomes;
	static BiomeGenBase[] chillyBiomes;
	static BiomeGenBase[] coldBiomes;	
	
	/**
	 * Registers a biome.
	 * @param biomeToAdd
	 * @param biomeType
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static final void registerBiome(BiomeGenBase biomeToAdd, EnumBiomeType biomeType) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{	
		switch (biomeType.value)
		{
			case 0:
				
				hotBiomes[hotBiomes.length] = biomeToAdd;
				break;
			
			case 1:
				
				temperateBiomes[temperateBiomes.length] = biomeToAdd;
				break;
			
			case 2:
				
				chillyBiomes[chillyBiomes.length] = biomeToAdd;
				break;
				
			case 3:
				
				coldBiomes[coldBiomes.length] = biomeToAdd;
				break;
			
			case 4:
				
				coldBiomes[coldBiomes.length] = biomeToAdd;
				chillyBiomes[chillyBiomes.length] = biomeToAdd;
				temperateBiomes[temperateBiomes.length] = biomeToAdd;
				hotBiomes[hotBiomes.length] = biomeToAdd;
				break;
		}
	}
	
	public static final void registerAllBiomes(GenLayerBiome biomeGenLayer)
	{
		try 
		{
			BiomeGenBase[] oldHotBiomes = (BiomeGenBase[]) ReflectionManipulateValues.getValue(GenLayerBiome.class, biomeGenLayer, "field_151623_c");
			BiomeGenBase[] oldTemperateBiomes = (BiomeGenBase[]) ReflectionManipulateValues.getValue(GenLayerBiome.class, biomeGenLayer, "field_151621_d");
			BiomeGenBase[] oldChillyBiomes = (BiomeGenBase[]) ReflectionManipulateValues.getValue(GenLayerBiome.class, biomeGenLayer, "field_151622_e");
			BiomeGenBase[] oldColdBiomes = (BiomeGenBase[]) ReflectionManipulateValues.getValue(GenLayerBiome.class, biomeGenLayer, "field_151620_f");
			
			for (int i = 0; i == hotBiomes.length; i++)
			{
				oldHotBiomes[oldHotBiomes.length] = hotBiomes[i];
			}
			
			for (int i = 0; i == temperateBiomes.length; i++)
			{
				oldTemperateBiomes[oldTemperateBiomes.length] = temperateBiomes[i];
			}
			
			for (int i = 0; i == chillyBiomes.length; i++)
			{
				oldChillyBiomes[oldChillyBiomes.length] = chillyBiomes[i];
			}
			
			for (int i = 0; i == coldBiomes.length; i++)
			{
				oldColdBiomes[oldColdBiomes.length] = coldBiomes[i];
			}
			
			ReflectionManipulateValues.setValue(GenLayerBiome.class, biomeGenLayer, "field_151620_f", coldBiomes);
			ReflectionManipulateValues.setValue(GenLayerBiome.class, biomeGenLayer, "field_151622_e", chillyBiomes);
			ReflectionManipulateValues.setValue(GenLayerBiome.class, biomeGenLayer, "field_151621_d", temperateBiomes);
			ReflectionManipulateValues.setValue(GenLayerBiome.class, biomeGenLayer, "field_151623_c", hotBiomes);
		} 
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) 
		{
			e.printStackTrace();
			System.err.println("An error occured registering the biomes!");
		}
	}
}
