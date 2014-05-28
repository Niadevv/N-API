package co.uk.niadel.api.client;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.tileentity.TileEntity;

public abstract class GUIDisplayer
{
	public static void displayGUI(String guiId, EntityPlayerMP player, TileEntity tileEntity, String name, boolean isLocalised, int inventorySlotSize, Container container, Object... otherInfo)
	{
		player.getNextWindowId();
    	player.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(player.currentWindowId, 8, name, inventorySlotSize, isLocalised));
    	player.openContainer = container;
    	player.openContainer.windowId = player.currentWindowId;
    	player.openContainer.addCraftingToCrafters(player);
	}
}
