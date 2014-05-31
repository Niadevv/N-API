package co.uk.niadel.mpi.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

/**
 * Where to register mobs. This makes entities summonable with /summon and (if you want) gives them spawn eggs.
 * @author Niadel
 */
public final class EntityRegistry extends EntityList
{
	/**
	 * {@inheritDoc}
	 * @param theClass
	 * @param entityId
	 * @param numericId
	 */
	public static final void registerEntity(Class<? extends Entity> theClass, String entityId, int numericId)
    {
		addMapping(theClass, entityId, numericId);
    }
	
    /**
     * Registers an entity with an associated spawn egg.
     * 
     * @param entityClass
     * @param stringMobId
     * @param numericMobId
     * @param eggBackgroundColour
     * @param eggSpotColour
     */
    public static final void registerEntityWithSpawnEgg(Class<? extends Entity> entityClass, String stringMobId, int numericMobId, int eggBackgroundColour, int eggSpotColour)
    {
		addMapping(entityClass, stringMobId, numericMobId, eggBackgroundColour, eggSpotColour);
	}
}
