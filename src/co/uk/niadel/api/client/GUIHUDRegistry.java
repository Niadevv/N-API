package co.uk.niadel.api.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiIngame;

public class GUIHUDRegistry
{
	public static final List<ISpecialHUDRenderer> renderers = new ArrayList<>();
	
	/**
	 * Registers a GUI renderer.
	 * @param renderer
	 */
	public static final void registerGUIHUDRenderer(ISpecialHUDRenderer renderer)
	{
		renderers.add(renderer);
	}
	
	/**
	 * Used to do the actual rendering.
	 */
	public static final void callAllRenderers(GuiIngame gui)
	{
		Iterator<ISpecialHUDRenderer> iterator = renderers.iterator();
		
		while (iterator.hasNext())
		{
			iterator.next().renderGUIElement(gui);
		}
	}
}
