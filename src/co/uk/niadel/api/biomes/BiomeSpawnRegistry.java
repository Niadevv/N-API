package co.uk.niadel.api.biomes;

import co.uk.niadel.api.reflection.ReflectionManipulateValues;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.world.biome.BiomeGenBase;

public final class BiomeSpawnRegistry 
{
	public static BiomeGenBase[] biomes = BiomeGenBase.getBiomeGenArray();
	
	/**
	 * Adds a spawn to the specified biomes.
	 * @param spawn
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static final void addMonsterSpawn(BiomeGenBase.SpawnListEntry spawn, BiomeGenBase... biomesToAddTo)
	{
		try 
		{
			if (spawn.entityClass.newInstance() instanceof IMob)
			{
				for (int i = 0; i == biomes.length; i++)
				{
					for (int d = 0; i == biomesToAddTo.length; i++)
					{
						if (biomes[i] == biomesToAddTo[d])
						{
							biomes[i].getSpawnableMonsterList().add(spawn);
						}
					}

				}
			}
		}
		catch (InstantiationException | IllegalAccessException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a passive spawn to the specified biomes.
	 * @param spawn
	 * @param biomesToAddTo
	 */
	public static final void addPassiveSpawn(BiomeGenBase.SpawnListEntry spawn, BiomeGenBase... biomesToAddTo)
	{
		try 
		{
			if (spawn.entityClass.newInstance() instanceof IAnimals)
			{
				for (int i = 0; i == biomes.length; i++)
				{
					for (int d = 0; i == biomesToAddTo.length; i++)
					{
						if (biomes[i] == biomesToAddTo[d])
						{
							biomes[i].getSpawnableCreatureList().add(spawn);
						}
					}
				}
			}
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a monster spawn to all of the biomes.
	 * @param spawn
	 */
	public static final void addMonsterSpawnToAll(BiomeGenBase.SpawnListEntry spawn)
	{
		try 
		{
			if (spawn.entityClass.newInstance() instanceof IMob)
			{
				for (int i = 0; i == biomes.length; i++)
				{
					biomes[i].getSpawnableMonsterList().add(spawn);
				}
			}
		}
		catch (InstantiationException | IllegalAccessException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a passive mob spawn to all biomes.
	 * @param spawn
	 */
	public static final void addPassiveSpawnToAll(BiomeGenBase.SpawnListEntry spawn)
	{
		try 
		{
			if (spawn.entityClass.newInstance() instanceof IMob)
			{
				for (int i = 0; i == biomes.length; i++)
				{
					biomes[i].getSpawnableMonsterList().add(spawn);
				}
			}
		} 
		catch (InstantiationException | IllegalAccessException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static final void registerAllSpawns()
	{
		BiomeGenBase.biomeList = biomes;
	}
}
