package co.uk.niadel.napi.common;

import co.uk.niadel.commons.reflection.ReflectionManipulateValues;
import co.uk.niadel.napi.util.MCData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourcePack;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides access to certain parts of vanilla.
 *
 * @author Niadel.
 */
public class MCHooks
{
	public static final List<IResourcePack> getDefaultResourcePacks()
	{
		if (MCData.isClientSide())
		{
			List<IResourcePack> defaultResourcePacks = ReflectionManipulateValues.getValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks");
			return defaultResourcePacks;
		}
		else
		{
			return new ArrayList<>();
		}
	}

	public static final void addResourcePacksToDefault(IResourcePack resourcePack)
	{
		if (MCData.isClientSide())
		{
			List<IResourcePack> defaultResourcePacks = getDefaultResourcePacks();
			defaultResourcePacks.add(resourcePack);

			ReflectionManipulateValues.setValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks", defaultResourcePacks);
		}
	}
}
