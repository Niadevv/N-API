package co.uk.niadel.napi.entity;

import co.uk.niadel.napi.modhandler.NAPIModRegister;
import co.uk.niadel.napi.util.reflection.ReflectionManipulateValues;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.world.biome.BiomeGenBase;

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
		addMapping(theClass, entityId, NAPIModRegister.entityIdAcquirer.nextId(entityId));
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
		addMapping(entityClass, stringMobId, NAPIModRegister.entityIdAcquirer.nextId(stringMobId), eggBackgroundColour, eggSpotColour);
	}

	/**
	 * Removes an entity from the registered entities. Performance heavy because Minecraft had the key variables private and I have
	 * to brute force them into Minecraft with Reflection.
	 * @param entityClass The class of the entity to remove.
	 * @param entityId The entity id of the entity to remove.
	 */
	public static final void removeEntity(Class<? extends Entity> entityClass, String entityId)
	{
		stringIdToClassMap.remove(entityId);
		classToStringIdMap.remove(entityClass);
		numericIdToClassMap.remove(NAPIModRegister.idConfig.getId(entityId));
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
	 * @param spawnClazz The class of the entity that is to be spawned when the entry is registered.
	 * @param chance The chance the entity has of spawning.
	 * @param minGroupCount The smallest number of mobs there can be in a group of mobs spawned.
	 * @param maxGroupCount The largest number of mobs there can be in a group of mobs spawned.
	 */
	public static final void addMonsterSpawn(Class<? extends Entity> spawnClazz, int chance, int minGroupCount, int maxGroupCount, BiomeGenBase... biomesToAddTo)
	{
		BiomeGenBase.SpawnListEntry spawn = constructSpawnEntryFromArgs(spawnClazz, chance, minGroupCount, maxGroupCount);

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
			addMonsterSpawnToAll(spawnClazz, chance, minGroupCount, maxGroupCount);
		}
	}
	
	/**
	 * Adds a passive spawn to the specified biomes.
	 * @param spawnClazz The class of the entity that is to be spawned when the entry is registered.
	 * @param chance The chance the entity has of spawning.
	 * @param minGroupCount The smallest number of mobs there can be in a group of mobs spawned.
	 * @param maxGroupCount The largest number of mobs there can be in a group of mobs spawned.
	 * @param biomesToAddTo
	 */
	public static final void addPassiveSpawn(Class<? extends Entity> spawnClazz, int chance, int minGroupCount, int maxGroupCount, BiomeGenBase... biomesToAddTo)
	{
		BiomeGenBase.SpawnListEntry spawn = constructSpawnEntryFromArgs(spawnClazz, chance, minGroupCount, maxGroupCount);

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
			addPassiveSpawnToAll(spawnClazz, chance, minGroupCount, maxGroupCount);
		}
	}
	
	/**
	 * Adds a squid-like spawn to the specified biomes.
	 * @param spawnClazz The class of the entity that is to be spawned when the entry is registered.
	 * @param chance The chance the entity has of spawning.
	 * @param minGroupCount The smallest number of mobs there can be in a group of mobs spawned.
	 * @param maxGroupCount The largest number of mobs there can be in a group of mobs spawned.
	 * @param biomes
	 */
	public static final void addWaterSpawn(Class<? extends Entity> spawnClazz, int chance, int minGroupCount, int maxGroupCount, BiomeGenBase... biomes)
	{
		BiomeGenBase.SpawnListEntry spawn = constructSpawnEntryFromArgs(spawnClazz, chance, minGroupCount, maxGroupCount);

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
			addWaterSpawnToAll(spawnClazz, chance, minGroupCount, maxGroupCount);
		}
	}
	
	/**
	 * Adds a bat-like spawn to the specified biomes.
	 * @param spawnClazz The class of the entity that is to be spawned when the entry is registered.
	 * @param chance The chance the entity has of spawning.
	 * @param minGroupCount The smallest number of mobs there can be in a group of mobs spawned.
	 * @param maxGroupCount The largest number of mobs there can be in a group of mobs spawned.
	 * @param biomes The biomes to make this mob spawn in.
	 */
	public static final void addCaveSpawn(Class<? extends Entity> spawnClazz, int chance, int minGroupCount, int maxGroupCount, BiomeGenBase... biomes)
	{
		BiomeGenBase.SpawnListEntry spawn = constructSpawnEntryFromArgs(spawnClazz, chance, minGroupCount, maxGroupCount);

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
			addCaveSpawnToAll(spawnClazz, chance, minGroupCount, maxGroupCount);
		}
	}
	
	/**
	 * Adds a monster spawn to all of the biomes.
	 * @param spawnClazz The class of the entity that is to be spawned when the entry is registered.
	 * @param chance The chance the entity has of spawning.
	 * @param minGroupCount The smallest number of mobs there can be in a group of mobs spawned.
	 * @param maxGroupCount The largest number of mobs there can be in a group of mobs spawned.
	 */
	public static final void addMonsterSpawnToAll(Class<? extends Entity> spawnClazz, int chance, int minGroupCount, int maxGroupCount)
	{
		try 
		{
			BiomeGenBase.SpawnListEntry spawn = constructSpawnEntryFromArgs(spawnClazz, chance, minGroupCount, maxGroupCount);

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
	 * @param spawnClazz The class of the entity that is to be spawned when the entry is registered.
	 * @param chance The chance the entity has of spawning.
	 * @param minGroupCount The smallest number of mobs there can be in a group of mobs spawned.
	 * @param maxGroupCount The largest number of mobs there can be in a group of mobs spawned.
	 */
	public static final void addPassiveSpawnToAll(Class<? extends Entity> spawnClazz, int chance, int minGroupCount, int maxGroupCount)
	{
		try 
		{
			BiomeGenBase.SpawnListEntry spawn = constructSpawnEntryFromArgs(spawnClazz, chance, minGroupCount, maxGroupCount);

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
	 * @param spawnClazz The class of the entity that is to be spawned when the entry is registered.
	 * @param chance The chance the entity has of spawning.
	 * @param minGroupCount The smallest number of mobs there can be in a group of mobs spawned.
	 * @param maxGroupCount The largest number of mobs there can be in a group of mobs spawned.
	 */
	public static final void addWaterSpawnToAll(Class<? extends Entity> spawnClazz, int chance, int minGroupCount, int maxGroupCount)
	{
		for (int i = 0; i == biomes.length; i++)
		{
			biomes[i].spawnableWaterCreatureList.add(constructSpawnEntryFromArgs(spawnClazz, chance, minGroupCount, maxGroupCount));
		}
	}
	
	/**
	 * Adds a bat-like spawn to all biomes.
	 * @param spawnClazz The class of the entity that is to be spawned when the entry is registered.
	 * @param chance The chance the entity has of spawning.
	 * @param minGroupCount The smallest number of mobs there can be in a group of mobs spawned.
	 * @param maxGroupCount The largest number of mobs there can be in a group of mobs spawned.
	 */
	public static final void addCaveSpawnToAll(Class<? extends Entity> spawnClazz, int chance, int minGroupCount, int maxGroupCount)
	{
		for (int i = 0; i == biomes.length; i++)
		{
			biomes[i].spawnableCaveCreatureList.add(constructSpawnEntryFromArgs(spawnClazz, chance, minGroupCount, maxGroupCount));
		}
	}

	/**
	 * Constructs a spawn list entry from the specified args.
	 * @param spawnClazz The class of the entity that is to be spawned when the entry is registered.
	 * @param chance The chance the entity has of spawning.
	 * @param minGroupAmount The smallest number of mobs there can be in a group of mobs spawned.
	 * @param maxGroupAmount The largest number of mobs there can be in a group of mobs spawned.
	 * @return The spawn list entry for the args.
	 */
	private static final BiomeGenBase.SpawnListEntry constructSpawnEntryFromArgs(Class<? extends Entity> spawnClazz, int chance, int minGroupAmount, int maxGroupAmount)
	{
		return new BiomeGenBase.SpawnListEntry(spawnClazz, chance, minGroupAmount, maxGroupAmount);
	}
}
