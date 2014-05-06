package co.uk.niadel.api.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

/**
 * Where to register mobs.
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
	public static final void addMapping(Class<? extends Entity> theClass, String entityId, int numericId)
    {
		addMapping(theClass, entityId, numericId);
    }

    /**
     * Adds a entity mapping with egg info. 
     * {@inheritDoc}
     */
    public static final void addMapping(Class<? extends Entity> entityClass, String stringId, int numericId, int eggBackgroundColour, int eggSpotColour)
    {	
    	addMapping(entityClass, stringId, numericId);
        entityEggs.put(Integer.valueOf(numericId), new EntityList.EntityEggInfo(numericId, eggBackgroundColour, eggSpotColour));
    }
    
    /**
     * Simplifies mob registration.
     * 
     * @param entityClass
     * @param stringMobId
     * @param numericMobId
     * @param eggBackgroundColour
     * @param eggSpotColour
     */
    public static final void registerEntityToList(Class<? extends Entity> entityClass, String stringMobId, int numericMobId, int eggBackgroundColour, int eggSpotColour)
    {
		addMapping(entityClass, stringMobId, numericMobId, eggBackgroundColour, eggSpotColour);
	}
    
    /**
     * Registers without a mob egg.
     * @param entityClass
     * @param stringMobId
     * @param numericMobId
     */
    public static final void registerEntityToList(Class<? extends Entity> entityClass, String stringMobId, int numericMobId)
    {
    	addMapping(entityClass, stringMobId, numericMobId);
    }
}
