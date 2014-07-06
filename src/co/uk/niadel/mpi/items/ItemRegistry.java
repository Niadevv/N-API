package co.uk.niadel.mpi.items;

import net.minecraft.item.Item;
import net.minecraft.util.RegistryNamespaced;
import co.uk.niadel.mpi.util.UniqueNumberAcquirer;

/**
 * Where you register Items.
 * @author Niadel
 */
public final class ItemRegistry
{
	public static RegistryNamespaced registry = Item.itemRegistry;
	
	/**
	 * Registers the item itself. Like FML, it handles numeric ids internally.
	 */
	public static final void registerItem(String nonNumericId, Item object)
	{
		registry.addObject(UniqueNumberAcquirer.getFreeInt(2268), nonNumericId, object);
	}
}
