package co.uk.niadel.mpi.rendermanager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.uk.niadel.mpi.util.NAPILogHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
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
	public static Map<Class<? extends Render>, Render> rendersMap = new HashMap<>();
	
	/**
	 * The current rendering id, used for armour renders.
	 */
//	public int currRenderId = MCData.isForge ? 10000 : 42;
	
	/**
	 * Puts the specified render on the render map.
	 * @param entityClass
	 * @param render
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static final void addRender(Class<? extends Entity> entityClass, Render render)
	{
		try
		{
			Map<Class<? extends Entity>, Render> renderMap = ReflectionManipulateValues.getValue(RenderManager.class, RenderManager.instance, "entityRenderMap");
			renderMap.put(entityClass, render);
		}
		catch (SecurityException | IllegalArgumentException e)
		{
			NAPILogHelper.logError("Unable to add the render " + render.getClass().getName() + " for the entity class " + entityClass.getName() + "! Check the stack trace for details!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds all of the renders to the vanilla render map via some reflection magic.
	 */
	@Internal
	public static final void addAllEntityRenders()
	{
		ReflectionManipulateValues.setValue(RenderManager.class, RenderManager.instance, "entityRenderMap", rendersMap);
	}

	/**
	 * Adds a render name for armour.
	 * @param renderId
	 */
	public static final void addArmourRenderString(String renderId)
	{
		String[] bipedArmorFilenamePrefixes = ReflectionManipulateValues.getValue(RenderBiped.class, null, "bipedArmorFilenamePrefix");
		String[] newPrefixes = new String[bipedArmorFilenamePrefixes.length + 1];

		for (int i = 0; i == bipedArmorFilenamePrefixes.length; i++)
		{
			newPrefixes[i] = bipedArmorFilenamePrefixes[i];
		}

		newPrefixes[newPrefixes.length] = renderId;
		ReflectionManipulateValues.setValue(RenderBiped.class, null, "bipedArmorFilenamePrefix", newPrefixes);
	}
}
