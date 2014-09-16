package co.uk.niadel.napi.forgewrapper;

import cpw.mods.fml.client.IModGuiFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.Set;

/**
 * The GUI Factory for N-API.
 */
public class NAPIConfigGUIFactory implements IModGuiFactory
{
	@Override
	public void initialize(Minecraft minecraftInstance) {

	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		if (Boolean.valueOf(System.getProperty("napi.disableForgeCFG", "false")))
		{
			return NAPIConfigGUI.class;
		}
		else
		{
			return null;
		}
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
	{
		return null;
	}
}
