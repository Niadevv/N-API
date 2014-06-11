package co.uk.niadel.mpi.biomes;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayerBiome;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.mpi.annotations.VersionMarkingAnnotations.TestFeature;
import co.uk.niadel.mpi.util.reflection.ReflectionManipulateValues;

@TestFeature(firstAppearance = "1.0")
/**
 * A system for registering biomes. At time of documenting, Forge has no obvious way of adding Biomes. 
 * This system is a more "brute force" method of adding biomes as it uses Reflection to "force" the biomes in.
 * @author Niadel
 */
public final class BiomeRegistry 
{
	static BiomeGenBase[] newHotBiomes;
	static BiomeGenBase[] newTemperateBiomes;
	static BiomeGenBase[] newChillyBiomes;
	static BiomeGenBase[] newColdBiomes;	
	
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
				
				newHotBiomes[newHotBiomes.length] = biomeToAdd;
				break;
			
			case 1:
				
				newTemperateBiomes[newTemperateBiomes.length] = biomeToAdd;
				break;
			
			case 2:
				
				newChillyBiomes[newChillyBiomes.length] = biomeToAdd;
				break;
				
			case 3:
				
				newColdBiomes[newColdBiomes.length] = biomeToAdd;
				break;
			
			case 4:
				
				newColdBiomes[newColdBiomes.length] = biomeToAdd;
				newChillyBiomes[newChillyBiomes.length] = biomeToAdd;
				newTemperateBiomes[newTemperateBiomes.length] = biomeToAdd;
				newHotBiomes[newHotBiomes.length] = biomeToAdd;
				break;
		}
	}
	
	@Internal
	public static final void registerAllBiomes(GenLayerBiome biomeGenLayer)
	{
		BiomeGenBase[] hotBiomes = (BiomeGenBase[]) ReflectionManipulateValues.getValue(GenLayerBiome.class, biomeGenLayer, "field_151623_c");
		BiomeGenBase[] temperateBiomes = (BiomeGenBase[]) ReflectionManipulateValues.getValue(GenLayerBiome.class, biomeGenLayer, "field_151621_d");
		BiomeGenBase[] chillyBiomes = (BiomeGenBase[]) ReflectionManipulateValues.getValue(GenLayerBiome.class, biomeGenLayer, "field_151622_e");
		BiomeGenBase[] coldBiomes = (BiomeGenBase[]) ReflectionManipulateValues.getValue(GenLayerBiome.class, biomeGenLayer, "field_151620_f");
			
		for (int i = 0; i == hotBiomes.length; i++)
		{
			hotBiomes[hotBiomes.length] = newHotBiomes[i];
		}
		
		for (int i = 0; i == temperateBiomes.length; i++)
		{
			temperateBiomes[temperateBiomes.length] = newTemperateBiomes[i];
		}
		
		for (int i = 0; i == chillyBiomes.length; i++)
		{
			chillyBiomes[chillyBiomes.length] = newChillyBiomes[i];
		}
		
		for (int i = 0; i == coldBiomes.length; i++)
		{
			coldBiomes[coldBiomes.length] = newColdBiomes[i];
		}
		
		ReflectionManipulateValues.setValue(GenLayerBiome.class, biomeGenLayer, "field_151620_f", coldBiomes);
		ReflectionManipulateValues.setValue(GenLayerBiome.class, biomeGenLayer, "field_151622_e", chillyBiomes);
		ReflectionManipulateValues.setValue(GenLayerBiome.class, biomeGenLayer, "field_151621_d", temperateBiomes);
		ReflectionManipulateValues.setValue(GenLayerBiome.class, biomeGenLayer, "field_151623_c", hotBiomes);
	}
}
