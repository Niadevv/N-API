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
	static Map<String, Object> materialRegistry = new HashMap<>();
	
	public MaterialRegistry(MapColor mapColour)
	{
		super(mapColour);
	}
	
	public static final void registerModMaterial(String registerId, Object material)
	{
		materialRegistry.put(registerId, material);
	}
	
	public static final Object getModMaterial(String id)
	{
		return materialRegistry.get(id);
	}
}
