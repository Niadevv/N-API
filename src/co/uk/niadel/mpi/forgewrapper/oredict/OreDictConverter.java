package co.uk.niadel.mpi.forgewrapper.oredict;

import java.util.Iterator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import co.uk.niadel.mpi.napioredict.NAPIOreDict;
import co.uk.niadel.mpi.util.NAPILogHelper;
import co.uk.niadel.mpi.util.reflection.ReflectionManipulateValues;

/**
 * This is in charge of taking Forge Ore Dictionary entries to N-API Ore Dictionary entries.
 * @author Niadel
 *
 */
public class OreDictConverter
{
	private static OreDictionary forgeOreDict = new OreDictionary();
	
	public static final boolean getForgeHasInit()
	{
		return ReflectionManipulateValues.getValue(OreDictionary.class, forgeOreDict, "hasInit");
	}
	
	/**
	 * Adds a Forge ore dictionary entry, used to convert NAPIOreDict entries to Forge.
	 * @param entryName
	 * @param items
	 */
	public static final void addForgeOreDictEntry(String entryName, ItemStack[] items)
	{	
		if (getForgeHasInit())
		{
			for (ItemStack item : items)
			{
				OreDictionary.registerOre(entryName, item);
				NAPILogHelper.log("Registered Forge Ore Dict entry " + entryName + ".");
			}
		}
	}
	
	/**
	 * Converts all of the N-API Ore Dictionary entries to Forge ones.
	 */
	public static final void addAllNAPIOreDictEntries()
	{
		Iterator<String> nameIterator = NAPIOreDict.oreDictEntries.keySet().iterator();
		
		while (nameIterator.hasNext())
		{
			OreDictConverter.addForgeOreDictEntry(nameIterator.next(), NAPIOreDict.oreDictEntries.get(nameIterator.next()));
		}
	}
}
