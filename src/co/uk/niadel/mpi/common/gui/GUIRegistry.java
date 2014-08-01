package co.uk.niadel.mpi.common.gui;

import co.uk.niadel.mpi.util.MCData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.HashMap;
import java.util.Map;

public final class GUIRegistry
{
	public static Map<String, IGUIRenderer> renderers = new HashMap<>();
	
	/**
	 * Adds a renderer.
	 * @param guiId
	 * @param renderer
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

	public static final void displayGui(GuiScreen screen)
	{
		if (MCData.isClientSide())
		{
			Minecraft.getMinecraft().displayGuiScreen(screen);
		}
	}
}
