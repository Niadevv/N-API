package co.uk.niadel.api.gen.structures;

import java.util.ArrayList;
import java.util.List;
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
	 * Adds a stucture that will always generate.
	 * @param structure
	 */
	public static void addNonMapFDependantStructure(MapGenStructure structure)
	{
		nonMapFDependantStructures.add(structure);
	}
	
	/**
	 * Adds a structure that will only generate if Map Features is enabled.
	 * @param structure
	 */
	public static void addMapFDependantStructure(MapGenStructure structure)
	{
		mapFDependantStructures.add(structure);
	}
}
