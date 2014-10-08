package co.uk.niadel.napi.gen.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.MapGenStructure;
import co.uk.niadel.napi.annotations.Internal;

/**
 * The registry for structures.
 *
 * @author Niadel
 */
public final class StructureRegistry
{
	/**
	 * Contains the structures that will generate regardless of whether or not
	 * Map Features is enabled.
	 */
	public static List<MapGenStructure> nonMapFDependantStructures = new ArrayList<>();
	
	/**
	 * Contains the structures that will generate only if Map Features is enabled.
	 */
	public static List<MapGenStructure> mapFDependantStructures = new ArrayList<>();
	
	/**
	 * A Map containing the information to be added to IO.
	 */
	public static Map<Class, String> ioStructureInformation = new HashMap<>();
	
	/**
	 * Adds a structure that will always generate, regardless of whether or not Map Features is enabled or disabled.
	 * @param structure The structure object to generate.
	 * @param structureClass The class of structure.
	 * @param shortStructId The shortened structure id of structureClass, I recommend to do something like "YOU_your_modid:SSid" for compatibility.
	 */
	public static void addNonMapFDependantStructure(MapGenStructure structure, Class structureClass, String shortStructId)
	{
		nonMapFDependantStructures.add(structure);
		ioStructureInformation.put(structureClass, shortStructId);
	}
	
	/**
	 * Adds a structure that will only generate if Map Features is enabled. Same params as
	 * @see StructureRegistry#addNonMapFDependantStructure(net.minecraft.world.gen.structure.MapGenStructure, Class, String)
	 */
	public static void addMapFDependantStructure(MapGenStructure structure, Class structureClass, String shortStructId)
	{
		mapFDependantStructures.add(structure);
		ioStructureInformation.put(structureClass, shortStructId);
	}
	
	/**
	 * Generates all Map Feature dependant structures.
	 * @param provider The provider to generate the structures in.
	 * @param worldObj The world object for provider.
	 * @param chunkCoordX Likely the chunk coord x.
	 * @param chunkCoordY Likely the chunk coord y.
	 * @param blocks An array of blocks to generate in.
	 */
	@Internal
	public static void generateAllMapFDependantStructures(IChunkProvider provider, World worldObj, int chunkCoordX, int chunkCoordY, Block[] blocks)
	{
		for (MapGenStructure mapGenStructure : mapFDependantStructures)
		{
			mapGenStructure.func_151539_a(provider, worldObj, chunkCoordX, chunkCoordY, blocks);
		}
	}
	
	/**
	 * Generates all non-Map Feature dependant structures. Params are the same as
	 * @see StructureRegistry#generateAllMapFDependantStructures
	 */
	@Internal
	public static void generateAllNonMapFDependantStructures(IChunkProvider provider, World worldObj, int par1, int par2, Block[] blocks)
	{
		for (MapGenStructure structure : nonMapFDependantStructures)
		{
			structure.func_151539_a(provider, worldObj, par1, par2, blocks);
		}
	}
}
