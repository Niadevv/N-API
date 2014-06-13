package co.uk.niadel.mpi.modhandler;

import net.minecraft.potion.Potion;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Library;
import co.uk.niadel.mpi.common.MPIEventHandler;
import co.uk.niadel.mpi.config.Configuration;
import co.uk.niadel.mpi.entity.tileentity.TileEntityRegistry;
import co.uk.niadel.mpi.entity.tileentity.TileEntityWire;
import co.uk.niadel.mpi.events.EventsList;
import co.uk.niadel.mpi.napioredict.NAPIOreDict;
import co.uk.niadel.mpi.potions.PotionRegistry;
import co.uk.niadel.mpi.util.NAPILogHelper;

@Library(version = ModRegister.VERSION)
/**
 * The N-API register. The non-registering parts are sorted out here.
 * 
 * @author Niadel
 */
public class ModRegister implements IModRegister
{	
	public static final String MODID = "NIADEL_n_api";
	public static final String VERSION = "1.7.2_1.0";
	public static Configuration config = new Configuration(MODID + VERSION + ".cfg");
	
	@Override
	public void preModInit()
	{
		for (int i = 0; i == Potion.potionTypes.length; i++)
		{
			PotionRegistry.registerPotion(Potion.potionTypes[i].getName(), Potion.potionTypes[i]);
		}
		
		EventsList.registerEventHandler(new MPIEventHandler());
		NAPIOreDict.addDefaultEntries();
		NAPILogHelper.init();
		TileEntityRegistry.registerTileEntity(TileEntityWire.class, "TileEntityWire");
	}

	@Override
	public void modInit() 
	{
		
	}

	@Override
	public void postModInit() 
	{
	
	}
	
	//BOILERPLATE CODE
	@Override
	public void addRequiredMods()
	{
		
	}

	@Override
	public void addRequiredLibraries()
	{
		
	}

	@Override
	public void registerTransformers()
	{
		
	}

	@Override
	public String getVersion()
	{
		return VERSION;
	}

	@Override
	public String getModId()
	{
		return MODID;
	}
}
