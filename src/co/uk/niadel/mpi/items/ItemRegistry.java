package co.uk.niadel.mpi.items;

import net.minecraft.item.Item;
import net.minecraft.util.RegistryNamespaced;
import co.uk.niadel.mpi.util.UniqueIdAcquirer;
import co.uk.niadel.mpi.modhandler.ModRegister;

/**
 * Where you register Items.
 * @author Niadel
 */
public final class ItemRegistry
{
	public static final RegistryNamespaced registry = Item.itemRegistry;
	
	/**
	 * Used to handle numeric ids internally.
	 */
	public static final UniqueIdAcquirer numAcquirer = ModRegister.idAcquirer;
	
	/**
	 * Registers the item itself. Like FML, it handles numeric ids internally.
	 */
	public static final void registerItem(String nonNumericId, Item object)
	{
		registry.addObject(numAcquirer.nextId(nonNumericId), nonNumericId, object);
	}
}
