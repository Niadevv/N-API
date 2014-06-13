package co.uk.niadel.mpi.events.client;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;

/**
 * Fired when a GUI from a mod is displayed.
 * @author Niadel
 *
 */
public class EventDisplayModGUI
{
	public String guiId, name;
	public EntityPlayerMP player;
	public boolean isLocalised;
	public int inventorySlotSize;
	public Container container;
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
