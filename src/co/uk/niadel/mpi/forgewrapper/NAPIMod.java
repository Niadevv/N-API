package co.uk.niadel.mpi.forgewrapper;

import co.uk.niadel.mpi.common.IConverter;
import co.uk.niadel.mpi.forgewrapper.measuresmpi.MeasureConverter;
import co.uk.niadel.mpi.init.Launch;
import co.uk.niadel.mpi.modhandler.ModRegister;
import co.uk.niadel.mpi.util.NAPILogHelper;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import net.minecraftforge.common.MinecraftForge;
import co.uk.niadel.mpi.common.NAPIData;
import co.uk.niadel.mpi.forgewrapper.eventhandling.EventHandlerFML;
import co.uk.niadel.mpi.forgewrapper.eventhandling.EventHandlerForge;
import co.uk.niadel.mpi.forgewrapper.oredict.OreDictConverter;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.mpi.util.MCData;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * Yes, I do know SOME Forge, but I have to ignore 500+ errors to do this :3.
 * @author Niadel
 *
 */
@Mod(modid = NAPIData.FORGE_MODID, version = NAPIData.FULL_VERSION, name = NAPIData.NAME, acceptedMinecraftVersions = NAPIData.MC_VERSION, guiFactory = "co.uk.niadel.mpi.forgewrapper.NAPIConfigGUIFactory")
public final class NAPIMod
{
	public static Configuration napiConfiguration;

	@Instance(NAPIData.FORGE_MODID)
	public NAPIMod instance;

	IConverter measureConverter = new MeasureConverter();
	IConverter oreDictConverter = new OreDictConverter();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		if (Launch.checkJavaVersion())
		{
			napiConfiguration = new Configuration(event.getSuggestedConfigurationFile());
			//Tell N-API that the game is a Forge environment.
			MCData.isForge = true;
			handleFMLIds();
			//Register the event handlers.
			MinecraftForge.EVENT_BUS.register(new EventHandlerForge());
			FMLCommonHandler.instance().bus().register(new EventHandlerFML());
			//Begin loading N-API mods.
			NModLoader.loadModsFromDir();
			NModLoader.callAllPreInits();
		}
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (Launch.checkJavaVersion())
		{
			NModLoader.callAllInits();
		}
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		if (Launch.checkJavaVersion())
		{
			NModLoader.callAllPostInits();
			measureConverter.convert();
			oreDictConverter.convert();
		}
	}

	/**
	 * Allows mods to test for mods from Forge.
	 */
	public static final void handleFMLIds()
	{
		List<ModContainer> fmlIds = Loader.instance().getModList();
		Iterator<ModContainer> modContainerIterator = fmlIds.iterator();

		while (modContainerIterator.hasNext())
		{
			NModLoader.forgeModids.add(modContainerIterator.next().getModId());
		}
	}

	public static final void updateConfig()
	{
		//Make sure the ids exist.
		for (Entry<String, String> currEntry : ModRegister.config.data.entrySet())
		{
			int currentForgeConfigValue = napiConfiguration.get("n-api", currEntry.getKey(), Integer.valueOf(currEntry.getValue())).getInt();

			//If the Forge configuration value for this particular ID is not equal to the N-API ID
			if (currentForgeConfigValue != Integer.valueOf(ModRegister.config.getConfigValue(currEntry.getValue())));
			{
				ModRegister.config.setConfigValue(currEntry.getKey(), String.valueOf(currentForgeConfigValue));
			}
		}
	}
}
