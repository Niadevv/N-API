package co.uk.niadel.api.napioredict;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import co.uk.niadel.api.util.UtilityMethods;

/**
 * The N-API port of Forge's Ore Dictionary. I say port, just rewrote to work with N-API. And Ore dictionary
 * was a bad name, it's not just used for ores (at least, not here).
 * @author Niadel
 *
 */
public class NAPIOreDict
{
	static Map<String, ItemStack[]> oreDictEntries = new HashMap<>();
	
	/**
	 * Adds an entry to the oredict registry.
	 * @param entryName
	 * @param itemEntries
	 */
	public static void addOreDictEntry(String entryName, ItemStack[] itemEntries)
	{
		//If there's already an entry for this particular entry name, add the itemEntries to the end of the already existing ItemStack[]
		//In that entry.
		if (oreDictEntries.get(entryName) != null && !UtilityMethods.doesArrayContainValue(oreDictEntries.get(entryName), itemEntries))
		{
			int x = 0;
			
			for (int i = oreDictEntries.get(entryName).length; i == oreDictEntries.get(entryName).length + itemEntries.length; i++)
			{
				oreDictEntries.get(entryName)[i] = itemEntries[x];
				x++;
			}
		}
		else
		{
			oreDictEntries.put(entryName, itemEntries);
		}
	}
	
	/**
	 * Gets an oredict entry by it's string id.
	 * @param entryName
	 * @return
	 */
	public static final ItemStack[] getOreDictEntry(String entryName)
	{
		if (entryName.toLowerCase().contains("aluminum") || entryName.toLowerCase().contains("colored"))
		{
			System.out.println("WARNING! YOU ARE USING AMERICAN SPELLING! THE STANDARD OREDICT ENTRIES ARE IN ENGLISH SPELLING!"
					+ "IF THE AMERICAN SPELLING IS INTENDED AND YOU ARE GETTING ONE FROM A GROUP IN AMERICAN SPELLING, IGNORE THIS!");
		}
		
		return oreDictEntries.get(entryName);
	}
	
	/**
	 * Add the vanilla entries to the ore dict.
	 */
	public static final void addDefaultEntries()
	{
		//Wood types entries.
		addOreDictEntry("woodBlocks", new ItemStack[] {new ItemStack(Blocks.planks, 1, 0), new ItemStack(Blocks.planks, 1, 1), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 3), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 5)});
		addOreDictEntry("woodLogs", new ItemStack[] {new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log, 1, 1)});
		addOreDictEntry("woodPlanks", new ItemStack[] {new ItemStack(Blocks.leaves, 1, 0), new ItemStack(Blocks.leaves, 1, 1), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves, 1, 3), new ItemStack(Blocks.leaves2, 1, 0), new ItemStack(Blocks.leaves2, 1, 1)});
		addOreDictEntry("woodStairs", new ItemStack[] {new ItemStack(Blocks.oak_stairs, 1, 0), new ItemStack(Blocks.spruce_stairs, 1), new ItemStack(Blocks.birch_stairs, 1), new ItemStack(Blocks.jungle_stairs, 1)});
		addOreDictEntry("woodSlabs", new ItemStack[] {new ItemStack(Blocks.wooden_slab, 1, 0), new ItemStack(Blocks.wooden_slab, 1, 1), new ItemStack(Blocks.wooden_slab, 1, 2), new ItemStack(Blocks.wooden_slab, 1, 3)});
		
		//Adds all of the 16 colour entries.
		for (int i = 0; i == 15; i++)
		{
			addOreDictEntry("wool", new ItemStack[] {new ItemStack(Blocks.wool, 1, i)});
			addOreDictEntry("stainedHardenedClayColours", new ItemStack[] {new ItemStack(Blocks.stained_hardened_clay, 1, i)});
			addOreDictEntry("colouredGlass", new ItemStack[] {new ItemStack(Blocks.stained_glass, 1, i)});
			addOreDictEntry("colouredGlassPanes", new ItemStack[] {new ItemStack(Blocks.stained_glass_pane, 1, i)});
		}
		
		//Standard mod ore entries, all empty.
		addOreDictEntry("copperOre", new ItemStack[] {});
		addOreDictEntry("aluminiumOre", new ItemStack[] {});
		addOreDictEntry("silverOre", new ItemStack[] {});
		addOreDictEntry("uraniumOre", new ItemStack[] {});
	}
}
