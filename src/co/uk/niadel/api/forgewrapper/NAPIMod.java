package co.uk.niadel.api.forgewrapper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.zip.ZipException;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import co.uk.niadel.api.asm.ASMRegistry;
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
			ASMRegistry.invokeAllTransformers();
			NModLoader.invokeRegisterMethods();
		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | InstantiationException | IOException e) //Oh sweet lawd the amount of exceptions :3
		{
			//Crash the game - If N-API fails to load in the Forge environment, stuff is broken.
			CrashReport crashReport = CrashReport.makeCrashReport(e, "Loading N-API Forge Wrapper @Mod Class");
			crashReport.makeCategory("Initialising N-API");
			throw new ReportedException(crashReport);
		}
	}
}
