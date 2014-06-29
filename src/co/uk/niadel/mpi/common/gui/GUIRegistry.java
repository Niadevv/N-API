package co.uk.niadel.mpi.common.gui;

import java.util.HashMap;
import java.util.Map;

public class GUIRegistry
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
}
