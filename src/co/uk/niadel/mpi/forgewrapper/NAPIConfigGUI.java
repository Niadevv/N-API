package co.uk.niadel.mpi.forgewrapper;

import co.uk.niadel.mpi.common.NAPIData;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

/**
 * The config GUI for N-API
 */
public class NAPIConfigGUI extends GuiConfig
{
	public NAPIConfigGUI(GuiScreen parent)
	{
		super(parent,
				new ConfigElement(NAPIMod.napiConfiguration.getCategory("n-api")).getChildElements(),
				NAPIData.FORGE_MODID, false, false, GuiConfig.getAbridgedConfigPath(NAPIMod.napiConfiguration.toString()));
	}
}
