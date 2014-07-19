package co.uk.niadel.mpi.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import co.uk.niadel.mpi.modhandler.ModRegister;

/**
 * Where to register mobs. This makes entities summonable with /summon and (if you want) gives them spawn eggs.
 * @author Niadel
 */
public final class EntityRegistry extends EntityList
{
	/**
	 * Registers an entity.
	 * @param theClass
	 * @param entityId
	 * @param numericId
	 */
	public static final void registerEntity(Class<? extends Entity> theClass, String entityId)
    {
		addMapping(theClass, entityId, ModRegister.entityIdAcquirer.nextId(entityId));
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
    public static final void registerEntity(Class<? extends Entity> entityClass, String stringMobId, int eggBackgroundColour, int eggSpotColour)
    {
		addMapping(entityClass, stringMobId, ModRegister.entityIdAcquirer.nextId(stringMobId), eggBackgroundColour, eggSpotColour);
	}
}
