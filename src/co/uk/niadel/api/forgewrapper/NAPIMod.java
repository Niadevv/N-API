package co.uk.niadel.api.forgewrapper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.zip.ZipException;
import co.uk.niadel.api.modhandler.loadhandler.NModLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Yes, I do know SOME Forge, but I have to ignore 500+ errors to do this :3.
 * @author Niadel
 *
 */
@Mod(modid = "NIADEL_n_api", version = "1.0", name = "N-API", acceptedMinecraftVersions = "1.7.2")
public class NAPIMod
{
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		try
		{
			NModLoader.loadModsFromDir();
		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | InstantiationException | IOException e) //Oh sweet lawd the amount of exceptions :3
		{
			System.err.println("ERROR LOADING N-API FORGE WRAPPER AND N-API MODS!");
			e.printStackTrace();
		}
	}
}
