package co.uk.niadel.mpi.gen.loot;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.WeightedRandomChestContent;

/**
 * The class that allows for adding loot to multiple loot chests.
 * 
 * TODO: Add Mineshaft loot adding, patch ComponentScatteredFeaturesPieces to add chest content
 * for all other than Jungle temple chests.
 * @author Niadel
 *
 */
public final class LootModifier
{
	/**
	 * The List that contains the chest content to add for jungle chests for mods.
	 */
	public static List<WeightedRandomChestContent> modChestContentJungle = new ArrayList<>();
	
	/**
	 * The List that contains the chest content to add for desert pyramid chests for mods.
	 */
	public static List<WeightedRandomChestContent> modChestContentDesert = new ArrayList<>();
	
	/**
	 * The List that contains the chest content to add for dungeon chests for mods.
	 */
	public static List<WeightedRandomChestContent> modChestContentDungeon = new ArrayList<>();
	
	/**
	 * Adds a loot spawn to chests.
	 * @param content
	 * @param lootLoc
	 */
	public static final void addLootSpawn(WeightedRandomChestContent content, EnumLootLocation lootLoc)
	{
		switch (lootLoc.locId)
		{
			case "Jungle":
				modChestContentJungle.add(content);
			
			case "Desert":
				modChestContentDesert.add(content);
			
			case "Dungeon":
				modChestContentDungeon.add(content);
		}
	}
	
	/**
	 * Adds loot spawns to multiple locations.
	 * @param content
	 * @param lootLocs
	 */
	public static final void addLootSpawns(WeightedRandomChestContent content, EnumLootLocation... lootLocs)
	{
		for (EnumLootLocation lootLoc : lootLocs)
		{
			addLootSpawn(content, lootLoc);
		}
	}
}
