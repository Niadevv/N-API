package co.uk.niadel.mpi.forgewrapper;

import net.minecraftforge.common.MinecraftForge;
import co.uk.niadel.mpi.asm.ASMRegistry;
import co.uk.niadel.mpi.common.NAPIData;
import co.uk.niadel.mpi.forgewrapper.eventhandling.EventHandlerFML;
import co.uk.niadel.mpi.forgewrapper.eventhandling.EventHandlerForge;
import co.uk.niadel.mpi.forgewrapper.oredict.OreDictConverter;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.mpi.util.GameDataAcquisitionUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Yes, I do know SOME Forge, but I have to ignore 500+ errors to do this :3. It is going to be
 * ****ing tedious to update versions. Thank you annotation borked logic u.u. If Java 8 fixes it,
 * I'm going to update to it for just the reason it fixes annotation logic. Forget Lambda 
 * expressions.
 * @author Niadel
 *
 */
@Mod(modid = "NIADEL_n_api", version = "early-alpha N-API-1.0.0", name = "N-API", acceptedMinecraftVersions = "1.7.2")
public class NAPIMod
{	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		//Tell N-API that the game is a Forge environment.
		GameDataAcquisitionUtils.isForge = true;
		//Register the event handlers.
		MinecraftForge.EVENT_BUS.register(new EventHandlerForge());
		FMLCommonHandler.instance().bus().register(new EventHandlerFML());
		//Begin loading N-API mods.
		NModLoader.loadModsFromDir();
		ASMRegistry.invokeAllTransformers();
		NModLoader.invokeRegisterMethods();
		OreDictConverter.addAllNAPIOreDictEntries();
	}
}
