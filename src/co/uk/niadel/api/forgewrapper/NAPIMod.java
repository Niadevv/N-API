package co.uk.niadel.api.forgewrapper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import net.minecraftforge.common.MinecraftForge;
import co.uk.niadel.api.asm.ASMRegistry;
import co.uk.niadel.api.exceptions.MCreatorDetectedException;
import co.uk.niadel.api.forgewrapper.eventhandling.EventHandlerFML;
import co.uk.niadel.api.forgewrapper.eventhandling.EventHandlerForge;
import co.uk.niadel.api.modhandler.loadhandler.NModLoader;
import co.uk.niadel.api.util.GameDataAcquisitionUtils;
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
		//Tell the game data that the game is a Forge environment.
		GameDataAcquisitionUtils.isForge = true;
		//Register the event handlers.
		MinecraftForge.EVENT_BUS.register(new EventHandlerForge());
		FMLCommonHandler.instance().bus().register(new EventHandlerFML());
		//Begin loading N-API mods.
		NModLoader.loadModsFromDir();
		ASMRegistry.invokeAllTransformers();
		NModLoader.invokeRegisterMethods();
	}
}
