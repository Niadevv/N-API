package co.uk.niadel.api.gen.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.MapGenStructure;

public class StructureRegistry
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
	 * @param structure
	 */
	public static void addNonMapFDependantStructure(MapGenStructure structure, Class structureClass, String shortStructId)
	{
		nonMapFDependantStructures.add(structure);
		ioStructureInformation.put(structureClass, shortStructId);
	}
	
	/**
	 * Adds a structure that will only generate if Map Features is enabled.
	 * @param structure
	 */
	public static void addMapFDependantStructure(MapGenStructure structure, Class structureClass, String shortStructId)
	{
		mapFDependantStructures.add(structure);
		ioStructureInformation.put(structureClass, shortStructId);
	}
	
	/**
	 * Generates all Map Feature dependant structures. I have no idea what par1 or par2
	 * does, you'll have to check MC's source.
	 * @param provider
	 * @param worldObj
	 * @param par1
	 * @param par2
	 * @param blocks
	 */
	public static void generateAllMapFDependantStructures(IChunkProvider provider, World worldObj, int par1, int par2, Block[] blocks)
	{
		Iterator<MapGenStructure> structureIterator = mapFDependantStructures.iterator();
		
		while (structureIterator.hasNext())
		{
			structureIterator.next().func_151539_a(provider, worldObj, par1, par2, blocks);
		}
	}
	
	/**
	 * Generates all non-Map Feature dependant structures.
	 * @param provider
	 * @param worldObj
	 * @param par1
	 * @param par2
	 * @param blocks
	 */
	public static void generateAllNonMapFDependantStructures(IChunkProvider provider, World worldObj, int par1, int par2, Block[] blocks)
	{
		//Oh my Notch, I love Generics in Iterators, I should have used it lots more. In fact,
		//when Beta hits, I'll do that. GENERICISE EVERYTHING!
		Iterator<MapGenStructure> structureIterator = nonMapFDependantStructures.iterator();
		
		while (structureIterator.hasNext())
		{
			structureIterator.next().func_151539_a(provider, worldObj, par1, par2, blocks);
		}
	}
}
