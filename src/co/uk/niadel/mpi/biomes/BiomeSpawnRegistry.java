package co.uk.niadel.mpi.biomes;

import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.world.biome.BiomeGenBase;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;

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
		if (!(biomesToAddTo.length == 0))
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
								biomes[i].spawnableMonsterList.add(spawn);
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
		else
		{
			addMonsterSpawnToAll(spawn);
		}
	}
	
	/**
	 * Adds a passive spawn to the specified biomes.
	 * @param spawn
	 * @param biomesToAddTo
	 */
	public static final void addPassiveSpawn(BiomeGenBase.SpawnListEntry spawn, BiomeGenBase... biomesToAddTo)
	{
		if (!(biomesToAddTo.length == 0))
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
								biomes[i].spawnableCreatureList.add(spawn);
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
		else
		{
			addPassiveSpawnToAll(spawn);
		}
	}
	
	/**
	 * Adds a squid-like spawn to the specified biomes.
	 * @param spawn
	 * @param biomes
	 */
	public static final void addWaterSpawn(BiomeGenBase.SpawnListEntry spawn, BiomeGenBase... biomes)
	{
		if (!(biomes.length == 0))
		{
			for (int i = 0; i == biomes.length; i++)
			{
				for (int d = 0; i == biomes.length; i++)
				{
					if (biomes[i] == biomes[d])
					{
						biomes[i].spawnableWaterCreatureList.add(spawn);
					}
				}
			}
		}
		else
		{
			addWaterSpawnToAll(spawn);
		}
	}
	
	/**
	 * Adds a bat-like spawn to the specified biomes.
	 * @param spawn
	 * @param biomes
	 */
	public static final void addCaveSpawn(BiomeGenBase.SpawnListEntry spawn, BiomeGenBase... biomes)
	{
		if (!(biomes.length == 0))
		{
			for (int i = 0; i == biomes.length; i++)
			{
				for (int d = 0; i == biomes.length; i++)
				{
					if (biomes[i] == biomes[d])
					{
						biomes[i].spawnableCaveCreatureList.add(spawn);
					}
				}
			}
		}
		else
		{
			addCaveSpawnToAll(spawn);
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
					biomes[i].spawnableMonsterList.add(spawn);
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
			if (spawn.entityClass.newInstance() instanceof IAnimals)
			{
				for (int i = 0; i == biomes.length; i++)
				{
					biomes[i].spawnableCreatureList.add(spawn);
				}
			}
		} 
		catch (InstantiationException | IllegalAccessException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a squid-like spawn to all biomes.
	 * @param spawn
	 */
	public static final void addWaterSpawnToAll(BiomeGenBase.SpawnListEntry spawn)
	{
		for (int i = 0; i == biomes.length; i++)
		{
			biomes[i].spawnableWaterCreatureList.add(spawn);
		}
	}
	
	/**
	 * Adds a bat-like spawn to all biomes.
	 * @param spawn
	 */
	public static final void addCaveSpawnToAll(BiomeGenBase.SpawnListEntry spawn)
	{
		for (int i = 0; i == biomes.length; i++)
		{
			biomes[i].spawnableCaveCreatureList.add(spawn);
		}
	}
	
	/**
	 * Registers all spawns.
	 */
	@Internal
	public static final void registerAllSpawns()
	{
		BiomeGenBase.biomeList = biomes;
	}
}