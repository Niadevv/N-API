package co.uk.niadel.napi.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.uk.niadel.napi.util.MCData;
import net.minecraft.client.gui.GuiIngame;
import co.uk.niadel.napi.annotations.MPIAnnotations.Internal;

public final class GUIHUDRegistry
{
	public static final List<ISpecialHUDRenderer> renderers = new ArrayList<>();
	
	/**
	 * Registers a GUI renderer.
	 * @param renderer The HUD renderer to add.
	 */
	public static final void registerGUIHUDRenderer(ISpecialHUDRenderer renderer)
	{
		renderers.add(renderer);
	}
	
	/**
	 * Used to do the actual rendering.
	 */
	@Internal
	public static final void callAllRenderers(GuiIngame gui)
	{
		if (MCData.isClientSide())
		{
			Iterator<ISpecialHUDRenderer> iterator = renderers.iterator();

			while (iterator.hasNext())
			{
				iterator.next().renderGUIElement(gui);
			}
		}
	}
}
