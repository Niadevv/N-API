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
	/**
	 * The vanilla item registry.
	 */
	public static final RegistryNamespaced registry = Item.itemRegistry;
	
	/**
	 * Used to handle numeric ids internally.
	 */
	public static final UniqueIdAcquirer numAcquirer = ModRegister.idAcquirer;
	
	/**
	 * Registers the item itself. Like FML, it handles numeric ids internally.
	 *
	 * @param nonNumericId The id to register the item under.
	 * @param object The Item to register.
	 */
	public static final void registerItem(String nonNumericId, Item object)
	{
		registry.addObject(numAcquirer.nextId(nonNumericId), nonNumericId, object);
	}
}
