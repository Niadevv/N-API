package co.uk.niadel.api.client;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.tileentity.TileEntity;
import co.uk.niadel.api.annotations.MPIAnnotations.ShouldSuperInSubclasses;
import co.uk.niadel.api.events.EventsList;
import co.uk.niadel.api.events.client.EventDisplayModGUI;

public abstract class GUIDisplayer
{
	/**
	 * Does the actual displaying of the GUI.
	 * @param guiId
	 * @param player
	 * @param tileEntity
	 * @param name
	 * @param isLocalised
	 * @param inventorySlotSize
	 * @param container
	 * @param otherInfo
	 */
	@ShouldSuperInSubclasses
	public static void displayGUI(String guiId, EntityPlayerMP player, String name, boolean isLocalised, int inventorySlotSize, Container container, Object... otherInfo)
	{
		player.getNextWindowId();
    	player.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(player.currentWindowId, 8, name, inventorySlotSize, isLocalised));
    	player.openContainer = container;
    	player.openContainer.windowId = player.currentWindowId;
    	player.openContainer.addCraftingToCrafters(player);
    	EventsList.fireEvent(new EventDisplayModGUI(guiId, player, name, isLocalised, inventorySlotSize, container, otherInfo));
	}
}
