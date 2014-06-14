package co.uk.niadel.mpi.events.client;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

/**
 * Fired when a GUI from a mod is displayed. I may make this cancellable, however, I'm not
 * sure if malicious modders will use this to enforce their vendetta against other mods.
 * @author Niadel
 */
public class EventDisplayModGUI
{
	/**
	 * The GUI's id and name.
	 */
	public String guiId, name;
	
	/**
	 * The player the GUI is being displayed to.
	 */
	public EntityPlayerMP player;
	
	/**
	 * Whether or not the name is localised (Eg. "Anvil") or not (Eg. "item.iron_pickaxe.name")
	 */
	public boolean isLocalised;
	
	/**
	 * The size of an inventory slot(?)
	 */
	public int inventorySlotSize;
	
	/**
	 * The container that the GUI's inventory uses.
	 */
	public Container container;
	
	/**
	 * Other info that the mod itself specifies.
	 */
	public Object[] otherInfo;
	
	public EventDisplayModGUI(String guiId, EntityPlayerMP player, String name, boolean isLocalised, int inventorySlotSize, Container container, Object... otherInfo)
	{
		this.guiId = guiId;
		this.player = player;
		this.isLocalised = isLocalised;
		this.inventorySlotSize = inventorySlotSize;
		this.container = container;
		this.otherInfo = otherInfo;
	}
}
