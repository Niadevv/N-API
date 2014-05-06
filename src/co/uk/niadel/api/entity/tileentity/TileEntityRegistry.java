package co.uk.niadel.api.entity.tileentity;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.tileentity.TileEntity;

/**
 * Where you register TileEntities.
 * @author Niadel
 *
 */
public final class TileEntityRegistry extends TileEntity
{
	/**
	 * A Map that contains tile entity IDs from mods.
	 */
	public static Map<Class<? extends TileEntity>, String> modTileEntitiesMap = new HashMap<>();
	
	/**
	 * Registers a tile entities to the vanilla registry.
	 * @param classToRegister
	 * @param id
	 */
	public final static void registerTileEntity(Class<? extends TileEntity> classToRegister, String id)
	{
		modTileEntitiesMap.put(classToRegister, id);
		func_145826_a(classToRegister, id);
	}
}
