package co.uk.niadel.mpi.modhandler;

import net.minecraft.potion.Potion;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Library;
import co.uk.niadel.mpi.asm.ASMRegistry;
import co.uk.niadel.mpi.asm.ASMTransformer;
import co.uk.niadel.mpi.asm.NAPIASMTransformer;
import co.uk.niadel.mpi.common.MPIEventHandler;
import co.uk.niadel.mpi.common.NAPIData;
import co.uk.niadel.mpi.config.Configuration;
import co.uk.niadel.mpi.entity.tileentity.TileEntityRegistry;
import co.uk.niadel.mpi.entity.tileentity.TileEntityWire;
import co.uk.niadel.mpi.events.EventsList;
import co.uk.niadel.mpi.napioredict.NAPIOreDict;
import co.uk.niadel.mpi.potions.PotionRegistry;
import co.uk.niadel.mpi.util.NAPILogHelper;

@Library(version = NAPIData.VERSION)
/**
 * The N-API register. The non-registering parts are sorted out here.
 * 
 * @author Niadel
 */
public final class ModRegister implements IModRegister
{	
	public static Configuration config = new Configuration(NAPIData.MODID + NAPIData.VERSION + ".cfg");
	
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
		NAPILogHelper.log("Finished Initialising Minecraft N-API version " + NAPIData.FULL_VERSION + "!");
	}

	@Override
	public void modInit() 
	{
		
	}

	@Override
	public void postModInit() 
	{
	
	}
	
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
		System.out.println("REGISTERING N-API ASM TRANSFORMER! Transformers, roll out!");
		ASMRegistry.registerTransformer(new NAPIASMTransformer());
	}

	@Override
	public String getVersion()
	{
		return NAPIData.VERSION;
	}

	@Override
	public String getModId()
	{
		return NAPIData.MODID;
	}
}
