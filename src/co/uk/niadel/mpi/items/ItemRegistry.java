package co.uk.niadel.mpi.items;

import java.util.Iterator;

import net.minecraft.item.Item;
import net.minecraft.util.RegistryNamespaced;

/**
 * Where you register Items.
 * @author Niadel
 */
public final class ItemRegistry
{
	private int numericId;
	private String nonNumericId;
	private String namespace;
	
	static RegistryNamespaced registry = Item.getRegistry();
	
	/**
	 * Registers the item, specify the namespace in nonNumericId EG. my_mod:some_item.
	 */
	public final static void registerItem(int numericId, String nonNumericId, Object object)
	{
		registry.addObject(numericId, nonNumericId, object);
	}
}
