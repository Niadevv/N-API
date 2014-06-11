package co.uk.niadel.mpi.rendermanager;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.mpi.util.reflection.ReflectionManipulateValues;

/**
 * Where to add entity renders.
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
	public static final void addRender(Class<? extends Render> entityClass, Render render) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Map<Class<? extends Render>, Render> renderMap = (HashMap<Class<? extends Render>, Render>) ReflectionManipulateValues.getValue(RenderManager.class, renderManager, "entityRenderMap");
		renderMap.put(entityClass, render);
	}
	
	/**
	 * Adds all of the renders.
	 */
	@Internal
	public static final void addAllRenders()
	{
		ReflectionManipulateValues.setValue(RenderManager.class, null, "instance", renderManager);
	}
}
