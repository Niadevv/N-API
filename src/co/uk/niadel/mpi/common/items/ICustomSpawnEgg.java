package co.uk.niadel.mpi.common.items;

import net.minecraft.entity.Entity;

/**
 * Implemented by items that spawn a mob like a spawn egg, but they don't want the item to look like a regular spawn egg.
 */
public interface ICustomSpawnEgg
{
	public Class<? extends Entity> getSpawnedEntity();
}
