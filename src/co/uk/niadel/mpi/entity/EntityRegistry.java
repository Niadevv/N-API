package co.uk.niadel.mpi.entity;

import co.uk.niadel.mpi.util.reflection.ReflectionCallMethods;
import co.uk.niadel.mpi.util.reflection.ReflectionManipulateValues;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.world.biome.BiomeGenBase;
import co.uk.niadel.mpi.modhandler.ModRegister;

import java.util.Map;

/**
 * Where to register mobs. This makes entities summonable with /summon and (if you want) gives them spawn eggs.
 * @author Niadel
 */
public final class EntityRegistry extends EntityList
{
	/**
	 * Only used to register entity spawns.
	 */
	private static BiomeGenBase[] biomes = BiomeGenBase.getBiomeGenArray();

	private static Map<String, Class<? extends Entity>> stringIdToClassMap = ReflectionManipulateValues.getValue(EntityList.class, null, "stringToClassMapping");

	private static Map<Class<? extends Entity>, String> classToStringIdMap = ReflectionManipulateValues.getValue(EntityList.class, null, "classToStringMapping");

	private static Map<Integer, Class<? extends Entity>> numericIdToClassMap = ReflectionManipulateValues.getValue(EntityList.class, null, "IDtoClassMapping");

	private static Map<Class<? extends Entity>, Integer> classToNumericIdMap = ReflectionManipulateValues.getValue(EntityList.class, null, "classToIDMapping");

	private static Map<String, Integer> stringIdToNumericIdMap = ReflectionManipulateValues.getValue(EntityList.class, null, "stringToIDMapping");
	
	/**
	 * Registers an entity. Has the advantage of numeric ids being handled internally.
	 * @param theClass The class of the entity to register.
	 * @param entityId The alphanumeric id of the entity to add.
	 */
	public static final void registerEntity(Class<? extends Entity> theClass, String entityId)
    {
		addMapping(theClass, entityId, ModRegister.entityIdAcquirer.nextId(entityId));
    }
	
    /**
     * Registers an entity with an associated spawn egg.
     * 
     * @param entityClass The class of the entity to add.
     * @param stringMobId The alphanumeric id of the entity to add.
     * @param eggBackgroundColour The numeric colour value of the Entity's spawn egg's area that is not the spots.
     * @param eggSpotColour The numeric colour value of the Entity's spawn egg's area that is the spots.
     */
    public static final void registerEntity(Class<? extends Entity> entityClass, String stringMobId, int eggBackgroundColour, int eggSpotColour)
    {
		addMapping(entityClass, stringMobId, ModRegister.entityIdAcquirer.nextId(stringMobId), eggBackgroundColour, eggSpotColour);
	}

	/**
	 * Removes an entity from the registered entities. Performance heavy because Minecraft had the key variables private.
	 * @param entityClass The class of the entity to remove.
	 * @param entityId The entity id of the entity to remove.
	 */
	public static final void removeEntity(Class<? extends Entity> entityClass, String entityId)
	{
		stringIdToClassMap.remove(entityId);
		classToStringIdMap.remove(entityClass);
		numericIdToClassMap.remove(ModRegister.config.getId(entityId));
		classToNumericIdMap.remove(entityClass);
		stringIdToNumericIdMap.remove(entityId);
		ReflectionManipulateValues.setValue(EntityList.class, null, "stringToClassMapping", stringIdToClassMap);
		ReflectionManipulateValues.setValue(EntityList.class, null, "classToStringMapping", classToStringIdMap);
		ReflectionManipulateValues.setValue(EntityList.class, null, "stringToClassMapping", numericIdToClassMap);
		ReflectionManipulateValues.setValue(EntityList.class, null, "classToIDMapping", classToNumericIdMap);
		ReflectionManipulateValues.setValue(EntityList.class, null, "stringToIDMapping", stringIdToClassMap);
	}
	
	/**
	 * Adds a spawn to the specified biomes.
	 * @param spawn
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
}
