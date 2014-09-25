package co.uk.niadel.napi.forgewrapper;

import co.uk.niadel.napi.common.NAPIData;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

/**
 * The idConfig GUI for N-API.
 */
public class NAPIConfigGUI extends GuiConfig
{
	public NAPIConfigGUI(GuiScreen parent)
	{
		//Borrowed and slightly modified code from Minalien's tutorial, one to fix an IntelliJ warning.
		super(parent, new ConfigElement<>(NAPIMod.napiConfiguration.getCategory("n-api")).getChildElements(), NAPIData.FORGE_MODID, false, false, GuiConfig.getAbridgedConfigPath(NAPIMod.napiConfiguration.toString()));
	}
}
