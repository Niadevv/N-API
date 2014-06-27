package co.uk.niadel.mpi.entity.tileentity;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.tileentity.TileEntity;

/**
 * Where you register TileEntities.
 * @author Niadel
 *
 */
public final class TileEntityRegistry
{
	/**
	 * A Map that contains tile entity IDs from mods.
	 */
	public static Map<String, Class<? extends TileEntity>> modTileEntitiesMap = new HashMap<>();
	
	/**
	 * Registers a tile entities to the vanilla registry.
	 * @param classToRegister
	 * @param id
	 */
	public static final void registerTileEntity(Class<? extends TileEntity> classToRegister, String id)
	{
		modTileEntitiesMap.put(id, classToRegister);
		TileEntity.func_145826_a(classToRegister, id);
	}
	
	/**
	 * Gets an entity class by it's String id.
	 * @param id
	 * @return
	 */
	public static final Class<? extends TileEntity> getClassById(String id)
	{
		return modTileEntitiesMap.get(id);
	}
}
