package co.uk.niadel.mpi.gen.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.MapGenStructure;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;

/**
 * The registry for structures.
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
	 * Adds a stucture that will always generate.
	 * @param structure The structure object to generate.
	 * @param structureClass The class of structure.
	 * @param shortStructId The shortened structure id of structureClass, I recommend to do something like "YOU_your_mod:SSid" for compatabillity.
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
	 * Generates all Map Feature dependant structures. I have no idea what par1 or par2
	 * does, you'll have to check MC's source. They're probably chunk coords.
	 * @param provider The provider to generate the strucutres in.
	 * @param worldObj The world object for provider.
	 * @param par1 I have no idea.
	 * @param par2 I have no idea.
	 * @param blocks An array of blocks to generate in.
	 */
	@Internal
	public static void generateAllMapFDependantStructures(IChunkProvider provider, World worldObj, int par1, int par2, Block[] blocks)
	{
		Iterator<MapGenStructure> structureIterator = mapFDependantStructures.iterator();
		
		while (structureIterator.hasNext())
		{
			structureIterator.next().func_151539_a(provider, worldObj, par1, par2, blocks);
		}
	}
	
	/**
	 * Generates all non-Map Feature dependant structures. Params are the same as
	 * @see StructureRegistry#generateAllMapFDependantStructures
	 */
	@Internal
	public static void generateAllNonMapFDependantStructures(IChunkProvider provider, World worldObj, int par1, int par2, Block[] blocks)
	{
		Iterator<MapGenStructure> structureIterator = nonMapFDependantStructures.iterator();
		
		while (structureIterator.hasNext())
		{
			structureIterator.next().func_151539_a(provider, worldObj, par1, par2, blocks);
		}
	}
}
