package co.uk.niadel.mpi.rendermanager;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.mpi.util.reflection.ReflectionManipulateValues;
import net.minecraft.entity.Entity;
import co.uk.niadel.mpi.util.MCData;

/**
 * Where to add entity renders.
 * @author Niadel
 *
 */
public final class RenderRegistry 
{
	private static Map<Class<? extends Render>, Render> rendersMap = new HashMap<>();
	
	/**
	 * The current rendering id, used for armour renders.
	 */
	public int currRenderId = MCData.isForge ? 10000 : 42;
	
	/**
	 * Puts the specified render on the render map.
	 * @param entityClass
	 * @param currRender
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static final void addRender(Class<? extends Entity> entityClass, Render render) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Map<Class<? extends Entity>, Render> renderMap = ReflectionManipulateValues.getValue(RenderManager.class, RenderManager.instance, "entityRenderMap");
		renderMap.put(entityClass, render);
	}
	
	/**
	 * Adds all of the renders to the vanilla render map via some reflection magic.
	 */
	@Internal
	public static final void addAllRenders()
	{
		ReflectionManipulateValues.setValue(RenderManager.class, RenderManager.instance, "entityRenderMap", rendersMap);
	}
}
