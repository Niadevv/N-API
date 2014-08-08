package co.uk.niadel.mpi.forgewrapper.oredict;

import java.util.Iterator;
import java.util.List;

import co.uk.niadel.mpi.common.IConverter;
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
public final class OreDictConverter implements IConverter
{
	/**
	 * Gets whether or not Forge ore dictionary has been initialised.
	 * @return
	 */
	public static final boolean getForgeHasInit()
	{
		return ReflectionManipulateValues.getValue(OreDictionary.class, null, "hasInit");
	}
	
	/**
	 * Adds a Forge ore dictionary entry, used to convert NAPIOreDict entries to Forge.
	 * @param entryName
	 * @param items
	 */
	public static final void addForgeOreDictEntry(String entryName, List<ItemStack> items)
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
	public final void convert()
	{
		Iterator<String> nameIterator = NAPIOreDict.oreDictEntries.keySet().iterator();
		
		while (nameIterator.hasNext())
		{
			OreDictConverter.addForgeOreDictEntry(nameIterator.next(), NAPIOreDict.oreDictEntries.get(nameIterator.next()));
		}
	}
}
