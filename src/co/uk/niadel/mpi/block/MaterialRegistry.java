package co.uk.niadel.mpi.block;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;

/**
 * Registers Materials. Not actually necessary, but it's a good idea to register it anyways.
 * @author Niadel
 */
public final class MaterialRegistry extends Material
{
	/**
	 * The materials.
	 */
	static Map<String, Material> materialRegistry = new HashMap<>();
	
	private MaterialRegistry(MapColor mapColour)
	{
		super(mapColour);
	}
	
	/**
	 * Registers the material.
	 * @param registerId
	 * @param material
	 */
	public static final void registerModMaterial(String registerId, Material material)
	{
		materialRegistry.put(registerId, material);
	}
	
	/**
	 * Gets a mod material from the registry.
	 * @param id
	 * @return
	 */
	public static final Object getModMaterial(String id)
	{
		return materialRegistry.get(id);
	}
}
