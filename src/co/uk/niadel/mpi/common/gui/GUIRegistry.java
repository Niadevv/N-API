package co.uk.niadel.mpi.common.gui;

import co.uk.niadel.mpi.util.MCData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * The registry for GUI displayers and a method for displaying a GuiScreen.
 */
public final class GUIRegistry
{
	public static Map<String, IGUIRenderer> renderers = new HashMap<>();
	
	/**
	 * Adds a renderer.
	 * @param guiId The string id of the GUI. It's best to have a naming convention like yourmodid:name_of_gui
	 * @param renderer The renderer to add.
	 */
	public static final void addRenderer(String guiId, IGUIRenderer renderer)
	{
		if (renderers.get(guiId) == null)
		{
			renderers.put(guiId, renderer);
		}
		else
		{
			throw new IllegalArgumentException(guiId + " is already registered as a GUI!");
		}
	}

	public static final void renderGUIRenderer(String guiId)
	{
		renderers.get(guiId).render();
	}

	public static final void displayGui(GuiScreen screen)
	{
		if (MCData.isClientSide())
		{
			Minecraft.getMinecraft().displayGuiScreen(screen);
		}
	}
}
