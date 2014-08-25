package co.uk.niadel.mpi.client;

import net.minecraft.client.gui.GuiIngame;

/**
 * Used to render things to the HUD display.
 */
public interface ISpecialHUDRenderer
{
	/**
	 * Where the rendering is done.
	 * @param gui The HUD GUI.
	 */
	public void renderGUIElement(GuiIngame gui);
}
