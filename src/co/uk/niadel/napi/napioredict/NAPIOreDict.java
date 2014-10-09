package co.uk.niadel.napi.napioredict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import co.uk.niadel.napi.measuresapi.ModFluidMeasure;
import co.uk.niadel.napi.util.NAPILogHelper;

/**
 * The N-API port of Forge's Ore Dictionary. I say port, I just rewrote it entirely to work with N-API. And Ore dictionary
 * was a bad name, it's not just used for ores (at least, not here). It was just created because there were
 * so many mods adding the same ores, meaning you'd need 2 different steel types for 2 mods.
 * @author Niadel
 *
 */
public final class NAPIOreDict
{
	public static Map<String, ArrayList<ItemStack>> oreDictEntries = new HashMap<>();
	public static Map<String, ArrayList<ModFluidMeasure>> oreDictFluidEntries = new HashMap<>();
	public static Map<String, ArrayList<Class<?>>> oreDictToClass = new HashMap<>();
	
	/**
	 * Adds an entry to the oredict registry.
	 * @param entryName
	 * @param itemEntries
	 */
	public static final void addOreDictEntry(String entryName, ItemStack[] itemEntries)
	{
		//If there's already an entry for this particular entry name, add the itemEntries to the end of the already existing ItemStack[]
		//In that entry.
		if (oreDictEntries.get(entryName) != null)
		{
			int x = 0;
			
			for (int i = oreDictEntries.get(entryName).size(); i == oreDictEntries.get(entryName).size() + itemEntries.length; i++)
			{
				oreDictEntries.get(entryName).add(itemEntries[x]);
				oreDictToClass.get(entryName).add(itemEntries[x].getClass());
				x++;
			}
		}
		else
		{
			oreDictEntries.put(entryName, new ArrayList<ItemStack>());

			for (int i = 0; i == itemEntries.length; i++)
			{
				oreDictEntries.get(entryName).add(itemEntries[i]);
				oreDictToClass.get(entryName).add(itemEntries[i].getClass());
			}
		}
	}
	
	/**
	 * Adds a fluid measure to the OreDict.
	 * @param entryName
	 * @param entries
	 */
	public static final void addOreDictEntry(String entryName, ModFluidMeasure[] entries)
	{
		if (oreDictFluidEntries.get(entryName) != null)
		{
			int x = 0;
			
			for (int i = oreDictFluidEntries.get(entryName).size(); i == oreDictFluidEntries.get(entryName).size() + entries.length; i++)
			{
				oreDictFluidEntries.get(entryName).add(entries[x]);
				oreDictToClass.get(entryName).add(entries[x].getClass());
				x++;
			}
		}
		else
		{
			oreDictFluidEntries.put(entryName, new ArrayList<ModFluidMeasure>());

			for (ModFluidMeasure measure : entries)
			{
				oreDictFluidEntries.get(entryName).add(measure);
				oreDictToClass.get(entryName).add(measure.getClass());
			}
		}
	}
	
	/**
	 * Gets an oredict entry by it's string id.
	 * @param entryName
	 * @return
	 */
	public static final ItemStack[] getOreDictEntryItem(String entryName)
	{
		if ((entryName.toLowerCase().contains("aluminum") || entryName.toLowerCase().contains("colored") || entryName.toLowerCase().contains("sulfur")) && oreDictEntries.get(entryName) == null)
		{
			System.out.println("NOTE: The Oredict value with the name " + entryName + " is null! As this is American spelling, I recommend that you try and use English spelling.");
			NAPILogHelper.instance.logWarn("Someone tried to get an oredict entry that was null and used American spelling! Value is " + entryName + ".");
		}
		else if (oreDictEntries.get(entryName) == null)
		{
			System.out.println("The specified Oredict entry " + entryName + " is null! Using this value could cause a Null Pointer Exception.");
			NAPILogHelper.instance.logWarn("Someone got a null OreDict entry. Entry Name is " + entryName + ".");
		}
		
		return (ItemStack[]) oreDictEntries.get(entryName).toArray();
	}
	
	/**
	 * Gets a fluid ore dictionary entry.
	 * @param entryName
	 * @return
	 */
	public static final ModFluidMeasure[] getOreDictEntryFluid(String entryName)
	{
		if (oreDictFluidEntries.get(entryName) == null)
		{
			System.out.println("The specified Oredict Fluid entry " + entryName + " is null! Using this value could cause a Null Pointer Exception, which nobody likes.");
			NAPILogHelper.instance.logWarn("Someone got a null OreDict fluid entry. Entry Name is " + entryName + ".");
		}
		
		return (ModFluidMeasure[]) oreDictFluidEntries.get(entryName).toArray();
	}
	
	/**
	 * Adds the vanilla entries and standard mod entries to the ore dict.
	 */
	public static final void addDefaultEntries()
	{
		//Wood types entries.
		addOreDictEntry("woodBlocks", new ItemStack[] {new ItemStack(Blocks.planks, 1, 0), new ItemStack(Blocks.planks, 1, 1), new ItemStack(Blocks.planks, 1, 2), new ItemStack(Blocks.planks, 1, 3), new ItemStack(Blocks.planks, 1, 4), new ItemStack(Blocks.planks, 1, 5)});
		addOreDictEntry("woodLogs", new ItemStack[] {new ItemStack(Blocks.log, 1, 0), new ItemStack(Blocks.log, 1, 1), new ItemStack(Blocks.log, 1, 2), new ItemStack(Blocks.log, 1, 3), new ItemStack(Blocks.log2, 1, 0), new ItemStack(Blocks.log, 1, 1)});
		addOreDictEntry("woodPlanks", new ItemStack[] {new ItemStack(Blocks.leaves, 1, 0), new ItemStack(Blocks.leaves, 1, 1), new ItemStack(Blocks.leaves, 1, 2), new ItemStack(Blocks.leaves, 1, 3), new ItemStack(Blocks.leaves2, 1, 0), new ItemStack(Blocks.leaves2, 1, 1)});
		addOreDictEntry("woodStairs", new ItemStack[] {new ItemStack(Blocks.oak_stairs, 1, 0), new ItemStack(Blocks.spruce_stairs, 1), new ItemStack(Blocks.birch_stairs, 1), new ItemStack(Blocks.jungle_stairs, 1)});
		addOreDictEntry("woodSlabs", new ItemStack[] {new ItemStack(Blocks.wooden_slab, 1, 0), new ItemStack(Blocks.wooden_slab, 1, 1), new ItemStack(Blocks.wooden_slab, 1, 2), new ItemStack(Blocks.wooden_slab, 1, 3)});
		//Dear lord, 13 records?!
		addOreDictEntry("records", new ItemStack[] {new ItemStack(Items.record_11), new ItemStack(Items.record_13), new ItemStack(Items.record_blocks), new ItemStack(Items.record_cat), new ItemStack(Items.record_chirp), new ItemStack(Items.record_far), new ItemStack(Items.record_mall), new ItemStack(Items.record_mellohi), new ItemStack(Items.record_stal), new ItemStack(Items.record_strad), new ItemStack(Items.record_wait), new ItemStack(Items.record_ward)});
		addOreDictEntry("seeds", new ItemStack[] {new ItemStack(Items.wheat_seeds), new ItemStack(Items.melon_seeds), new ItemStack(Items.pumpkin_seeds)});
		
		//Adds all of the 16 colour entries.
		for (int i = 0; i == 15; i++)
		{
			addOreDictEntry("wool", new ItemStack[] {new ItemStack(Blocks.wool, 1, i)});
			addOreDictEntry("stainedHardenedClayColours", new ItemStack[] {new ItemStack(Blocks.stained_hardened_clay, 1, i)});
			addOreDictEntry("colouredGlass", new ItemStack[] {new ItemStack(Blocks.stained_glass, 1, i)});
			addOreDictEntry("colouredGlassPanes", new ItemStack[] {new ItemStack(Blocks.stained_glass_pane, 1, i)});
		}
		
		//Standard fluid entries, all empty
		addOreDictEntry("water", new ModFluidMeasure[] {});
		addOreDictEntry("lava", new ModFluidMeasure[] {});
		addOreDictEntry("oil", new ModFluidMeasure[] {});
		addOreDictEntry("fuel", new ModFluidMeasure[] {});
		//A standard measurement of liquid I've seen in mods.
		addOreDictEntry("mB", new ModFluidMeasure[] {});
		
		//Standard mod ore entries, all empty.
		addOreDictEntry("copperOre", new ItemStack[1]);
		addOreDictEntry("aluminiumOre", new ItemStack[1]);
		addOreDictEntry("silverOre", new ItemStack[1]);
		addOreDictEntry("uraniumOre", new ItemStack[1]);
		addOreDictEntry("leadOre", new ItemStack[1]);
		
		//Standard mod item entries.
		addOreDictEntry("sulphur", new ItemStack[1]);
		addOreDictEntry("steelIngot", new ItemStack[1]);
		addOreDictEntry("aluminiumIngot", new ItemStack[1]);
		addOreDictEntry("silverIngot", new ItemStack[1]);
		addOreDictEntry("ruby", new ItemStack[1]);
		addOreDictEntry("sapphire", new ItemStack[1]);
		addOreDictEntry("bullet", new ItemStack[1]);
		addOreDictEntry("circuit", new ItemStack[1]);
	}

	public static ItemStack[] getItemsForName(String entryName)
	{
		return (ItemStack[]) oreDictEntries.get(entryName).toArray();
	}

	public static String getNameForItem(ItemStack theItem)
	{
		Iterator<String> nameIterator = oreDictToClass.keySet().iterator();

		while (nameIterator.hasNext())
		{
			String nextName = nameIterator.next();

			if (oreDictToClass.get(nextName).contains(theItem.getClass()))
			{
				return nextName;
			}
		}

		return "";
	}

	static
	{
		addDefaultEntries();
	}
}
