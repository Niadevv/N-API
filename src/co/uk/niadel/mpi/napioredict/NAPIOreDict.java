package co.uk.niadel.mpi.napioredict;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import co.uk.niadel.mpi.measuresmpi.ModFluidMeasure;
import co.uk.niadel.mpi.util.ArrayUtils;
import co.uk.niadel.mpi.util.NAPILogHelper;
import co.uk.niadel.mpi.util.UtilityMethods;

/**
 * The N-API port of Forge's Ore Dictionary. I say port, I just rewrote it entirely to work with N-API. And Ore dictionary
 * was a bad name, it's not just used for ores (at least, not here). It was just created because there were
 * so many mods adding the same ores, meaning you'd need 2 different steel types for 2 mods.
 * @author Niadel
 *
 */
public final class NAPIOreDict
{
	public static Map<String, ItemStack[]> oreDictEntries = new HashMap<>();
	public static Map<String, ModFluidMeasure[]> oreDictFluidEntries = new HashMap<>();
	
	/**
	 * Adds an entry to the oredict registry.
	 * @param entryName
	 * @param itemEntries
	 */
	public static final void addOreDictEntry(String entryName, ItemStack[] itemEntries)
	{
		//If there's already an entry for this particular entry name, add the itemEntries to the end of the already existing ItemStack[]
		//In that entry.
		if (oreDictEntries.get(entryName) != null && !ArrayUtils.doesArrayContainValue(oreDictEntries.get(entryName), itemEntries))
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
	 * Adds a fluid measure to the OreDict.
	 * @param entryName
	 * @param entries
	 */
	public static final void addOreDictEntry(String entryName, ModFluidMeasure[] entries)
	{
		if (oreDictFluidEntries.get(entryName) != null && !ArrayUtils.doesArrayContainValue(oreDictFluidEntries.get(entryName), entries))
		{
			int x = 0;
			
			for (int i = oreDictFluidEntries.get(entryName).length; i == oreDictFluidEntries.get(entryName).length + entries.length; i++)
			{
				oreDictFluidEntries.get(entryName)[i] = entries[x];
				x++;
			}
		}
		else
		{
			oreDictFluidEntries.put(entryName, entries);
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
			NAPILogHelper.logWarn("Someone tried to get an oredict entry that was null and used American spelling! Value is " + entryName + ".");
		}
		else if (oreDictEntries.get(entryName) == null)
		{
			System.out.println("The specified Oredict entry " + entryName + " is null! Using this value could cause a Null Pointer Exception.");
			NAPILogHelper.logWarn("Someone got a null OreDict entry. Entry Name is " + entryName + ".");
		}
		
		return oreDictEntries.get(entryName);
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
			NAPILogHelper.logWarn("Someone got a null OreDict fluid entry. Entry Name is " + entryName + ".");
		}
		
		return oreDictFluidEntries.get(entryName);
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
		addOreDictEntry("copperOre", new ItemStack[] {});
		addOreDictEntry("aluminiumOre", new ItemStack[] {});
		addOreDictEntry("silverOre", new ItemStack[] {});
		addOreDictEntry("uraniumOre", new ItemStack[] {});
		addOreDictEntry("leadOre", new ItemStack[] {});
		
		//Standard mod item entries.
		addOreDictEntry("sulphur", new ItemStack[] {});
		addOreDictEntry("steelIngot", new ItemStack[] {});
		addOreDictEntry("aluminiumIngot", new ItemStack[] {});
		addOreDictEntry("silverIngot", new ItemStack[] {});
		addOreDictEntry("ruby", new ItemStack[] {});
		addOreDictEntry("sapphire", new ItemStack[] {});
		addOreDictEntry("bullet", new ItemStack[] {});
		addOreDictEntry("circuit", new ItemStack[] {});
	}
}
