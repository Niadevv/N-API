package co.uk.niadel.api.rendermanager;

import java.util.HashMap;
import java.util.Map;
import co.uk.niadel.api.util.reflection.ReflectionManipulateValues;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;

/**
 * Where to add renders.
 * @author Niadel
 *
 */
public final class RenderRegistry 
{
	static RenderManager renderManager = RenderManager.instance;
	
	/**
	 * Puts the render in the renders list.
	 * @param entityClass
	 * @param currRender
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static final void addRender(Class entityClass, Render currRender) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Map<Class<?>, Render> renderMap = (HashMap<Class<?>, Render>) ReflectionManipulateValues.getValue(RenderManager.class, renderManager, "entityRenderMap");
		renderMap.put(entityClass, currRender);
	}
	
	/**
	 * Adds all of the renders.
	 */
	public static final void addAllRenders()
	{
		try
		{
			ReflectionManipulateValues.setValue(RenderManager.class, renderManager, "instance", renderManager);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
}
