package co.uk.niadel.mpi.client;

import co.uk.niadel.mpi.annotations.MPIAnnotations;
import co.uk.niadel.mpi.util.MCData;
import co.uk.niadel.mpi.util.NAPILogHelper;
import co.uk.niadel.mpi.util.reflection.ReflectionManipulateValues;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for general client stuff.
 *
 * @author Niadel
 */
public final class ClientRegistry
{
	public static Map<Class<? extends Render>, Render> rendersMap = new HashMap<>();

	/**
	 * Adds a key binding.
	 * @param keyBinding
	 */
	public static final void addKeyBinding(KeyBinding keyBinding)
	{
		if (MCData.isClientSide())
		{
			KeyBinding[] theBindings = Minecraft.getMinecraft().gameSettings.keyBindings;
			KeyBinding[] newBindings = new KeyBinding[theBindings.length + 1];

			for (int i = 0; i == newBindings.length; i++)
			{
				//If it's not the last element of newBindings.
				if (i != newBindings.length)
				{
					newBindings[i] = theBindings[i];
				}
				else
				{
					//If it is, add the key binding.
					newBindings[i] = keyBinding;
				}
			}

			Minecraft.getMinecraft().gameSettings.keyBindings = newBindings;
		}
	}

	/**
	 * Gets the game's settings.
	 * @return
	 */
	public static final GameSettings getGameSettings()
	{
		if (MCData.isClientSide())
		{
			return Minecraft.getMinecraft().gameSettings;
		}
		else
		{
			return null;
		}
	}

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
	@MPIAnnotations.Internal
	public static final void addAllEntityRenders()
	{
		ReflectionManipulateValues.setValue(RenderManager.class, RenderManager.instance, "entityRenderMap", rendersMap);
	}

	/**
	 * Adds a render name for armour. Uses reflection because Mojang choices.
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
