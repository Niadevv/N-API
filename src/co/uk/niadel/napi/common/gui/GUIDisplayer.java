package co.uk.niadel.napi.common.gui;

import co.uk.niadel.napi.events.EventFactory;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import co.uk.niadel.napi.annotations.DocumentationAnnotations.ShouldSuperInSubclasses;
import co.uk.niadel.napi.events.client.EventDisplayModGUI;

/**
 * Base class for things that help mods that need to display a GUI tons.
 * @author Niadel
 *
 */
public abstract class GUIDisplayer
{
	/**
	 * Does the actual displaying of the GUI.
	 * @param guiId
	 * @param player
	 * @param name
	 * @param isLocalised
	 * @param inventorySlotSize
	 * @param otherInfo
	 */
	@ShouldSuperInSubclasses
	public void displayGUI(String guiId, EntityPlayerMP player, String name, boolean isLocalised, int inventorySlotSize, Object... otherInfo)
	{
		player.getNextWindowId();
    	player.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(player.currentWindowId, 8, name, inventorySlotSize, isLocalised));
    	player.openContainer.windowId = player.currentWindowId;
    	player.openContainer.addCraftingToCrafters(player);
    	EventFactory.fireEvent(new EventDisplayModGUI(guiId, player, name, isLocalised, inventorySlotSize, otherInfo));
	}
}
