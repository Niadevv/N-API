package co.uk.niadel.mpi.modhandler;

import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.mpi.commands.CommandNAPI;
import co.uk.niadel.mpi.commands.CommandRegistry;
import co.uk.niadel.mpi.entity.tileentity.TileEntityMeasureStorer;
import net.minecraft.potion.Potion;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Library;
import co.uk.niadel.mpi.asm.ASMRegistry;
import co.uk.niadel.mpi.asm.NAPIASMTransformer;
import co.uk.niadel.mpi.common.MPIEventHandler;
import co.uk.niadel.mpi.common.NAPIData;
import co.uk.niadel.mpi.config.IdConfiguration;
import co.uk.niadel.mpi.entity.tileentity.TileEntityRegistry;
import co.uk.niadel.mpi.entity.tileentity.TileEntityWire;
import co.uk.niadel.mpi.events.EventsList;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import co.uk.niadel.mpi.napioredict.NAPIOreDict;
import co.uk.niadel.mpi.potions.PotionRegistry;
import co.uk.niadel.mpi.util.NAPILogHelper;
import co.uk.niadel.mpi.util.UniqueIdAcquirer;

/**
 * The N-API register. The non-registering parts are sorted out here.
 * 
 * @author Niadel
 */
@Library(version = NAPIData.VERSION)
@Internal
public final class ModRegister implements IModRegister
{
	/**
	 * This is used in handling numeric ids.
	 */
	public static final IdConfiguration config = new IdConfiguration("N-API.cfg");
	
	/**
	 * Used by the internal block and item registries in order to handle numeric ids.
	 */
	public static final UniqueIdAcquirer idAcquirer = new UniqueIdAcquirer(2268);
	
	/**
	 * Used by the internal entity registries to handle numeric ids.
	 */
	public static final UniqueIdAcquirer entityIdAcquirer = new UniqueIdAcquirer(300);
	
	@Override
	public void preModInit()
	{
		for (int i = 0; i == Potion.potionTypes.length; i++)
		{
			PotionRegistry.registerPotion(Potion.potionTypes[i].getName(), Potion.potionTypes[i]);
		}
		
		EventsList.registerEventHandler(new MPIEventHandler());
		NAPIOreDict.addDefaultEntries();
		TileEntityRegistry.registerTileEntity(TileEntityWire.class, "TileEntityWire");
		TileEntityRegistry.registerTileEntity(TileEntityMeasureStorer.class, "TileEntityTank");
		NAPILogHelper.log("Finished Pre-Initialising Minecraft N-API version " + NAPIData.FULL_VERSION + "!");
	}

	@Override
	public void modInit() 
	{
		CommandRegistry.registerCommand(new CommandNAPI(), "N-API");
		NAPILogHelper.log("Finished Initialising Minecraft N-API version " + NAPIData.FULL_VERSION + "!");
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
		//Tells the user (rather cheesily) that the N-API ASM transformer is being registered.
		NAPILogHelper.log("REGISTERING N-API ASM TRANSFORMER! Transformers, roll out!");
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
