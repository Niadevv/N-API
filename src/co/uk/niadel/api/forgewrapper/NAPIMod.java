package co.uk.niadel.api.forgewrapper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import net.minecraftforge.common.MinecraftForge;
import co.uk.niadel.api.asm.ASMRegistry;
import co.uk.niadel.api.forgewrapper.eventhandling.EventHandlerFML;
import co.uk.niadel.api.forgewrapper.eventhandling.EventHandlerForge;
import co.uk.niadel.api.modhandler.loadhandler.NModLoader;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
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
			//Register the event handlers.
			MinecraftForge.EVENT_BUS.register(new EventHandlerForge());
			FMLCommonHandler.instance().bus().register(new EventHandlerFML());
			//Begin loading N-API mods.
			NModLoader.loadModsFromDir();
			ASMRegistry.invokeAllTransformers();
			NModLoader.invokeRegisterMethods();
		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | InstantiationException | IOException e) //Oh sweet lawd the amount of exceptions :3
		{
			//Let the user know stuff is broken.
			FMLLog.severe("SERIOUS ISSUE OCCURED LOADING N-API FORGE WRAPPER! EXCEPTION IS BELOW:");
			e.printStackTrace();
		}
	}
}
