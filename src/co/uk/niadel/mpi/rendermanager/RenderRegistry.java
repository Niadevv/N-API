package co.uk.niadel.mpi.rendermanager;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.mpi.util.reflection.ReflectionManipulateValues;

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
	public static final void addRender(Class entityClass, Render render) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Map<Class<?>, Render> renderMap = (HashMap<Class<?>, Render>) ReflectionManipulateValues.getValue(RenderManager.class, renderManager, "entityRenderMap");
		renderMap.put(entityClass, render);
	}
	
	/**
	 * Adds all of the renders.
	 */
	@Internal
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
