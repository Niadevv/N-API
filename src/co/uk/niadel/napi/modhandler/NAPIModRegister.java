package co.uk.niadel.napi.modhandler;

import co.uk.niadel.napi.annotations.*;
import co.uk.niadel.napi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.napi.asm.transformers.*;
import co.uk.niadel.napi.commands.CommandNAPI;
import co.uk.niadel.napi.commands.CommandRegistry;
import co.uk.niadel.napi.entity.tileentity.TileEntityMeasureStorer;
import net.minecraft.potion.Potion;
import co.uk.niadel.napi.common.MPIEventHandler;
import co.uk.niadel.napi.common.NAPIData;
import co.uk.niadel.napi.config.IdConfiguration;
import co.uk.niadel.napi.entity.tileentity.TileEntityRegistry;
import co.uk.niadel.napi.entity.tileentity.TileEntityWire;
import co.uk.niadel.napi.napioredict.NAPIOreDict;
import co.uk.niadel.napi.potions.PotionRegistry;
import co.uk.niadel.napi.util.NAPILogHelper;
import co.uk.niadel.napi.util.UniqueIdAcquirer;

/**
 * The N-API register. The MPI parts are sorted out here.
 * 
 * @author Niadel
 */
@ModRegister(modId = NAPIData.MODID, version = NAPIData.VERSION)
@UnstableMod(warningMessage = "This is N-API alpha! N-API will likely change heavily before release, possibly breaking mods made with it!")
@Internal
public final class NAPIModRegister
{
	/**
	 * This is used in handling numeric ids.
	 */
	@Internal
	public static final IdConfiguration config = new IdConfiguration("N-API.cfg");

	/**
	 * Used in event handlers for optimisation. Not registered as it is called internally by EventFactory.
	 */
	@Immutable
	public static final NAPIASMEventHandlerTransformer asmEventHandler = new NAPIASMEventHandlerTransformer();

	/**
	 * Adds event calls and other necessary edits to Minecraft.
	 */
	@SuppressWarnings("unused")
	@ASMTransformer
	@Immutable
	public static final NAPIASMNecessityTransformer necessityTransformer = new NAPIASMNecessityTransformer();

	/**
	 * Gets rid of calls to System.exit(), Runtime.getRuntime().exit() and Runtime.getRuntime().halt().
	 */
	@SuppressWarnings("unused")
	@ASMTransformer
	@Immutable
	public static final NAPIASMDeGameExitingTransformer deGameExitingTransformer = new NAPIASMDeGameExitingTransformer();

	/**
	 * Removes calls to System.out.println and System.err.println to encourage the use of loggers, which really help with debugging.
	 */
	@SuppressWarnings("unused")
	@ASMTransformer
	@Immutable
	public static final NAPIASMDeSysOutTransformer deSysOutTransformer = new NAPIASMDeSysOutTransformer();

	/**
	 * The event handler for the N-API commons package utillities.
	 */
	@SuppressWarnings("unused")
	@EventHandler
	public static final MPIEventHandler eventHandler = new MPIEventHandler();

	/**
	 * Implements functionality for the @ReplacementFor annotation and some others.
	 */
	@SuppressWarnings("unused")
	@ASMTransformer
	public static final NAPIASMOptionalsTransformer optionalsTransformer = new NAPIASMOptionalsTransformer();

	/**
	 * Adds functionality for the @Immutable annotation.
	 */
	@SuppressWarnings("unused")
	@ASMTransformer
	@Immutable
	public static final NAPIASMImmutableTransformer immutablesTransformer = new NAPIASMImmutableTransformer();

	/**
	 * Used by the internal block and item registries in order to handle numeric ids.
	 */
	@Immutable
	public static final UniqueIdAcquirer idAcquirer = new UniqueIdAcquirer(2268);
	
	/**
	 * Used by the internal entity registries to handle numeric ids.
	 */
	@Immutable
	public static final UniqueIdAcquirer entityIdAcquirer = new UniqueIdAcquirer(300);

	/**
	 * Used by the DimensionIdRegistry to get a unique dimension id.
	 */
	@Immutable
	public static final UniqueIdAcquirer dimensionIdAcquirer = new UniqueIdAcquirer(2);

	@LoadStateMethod(EnumLoadState.PREINIT)
	public void preModInit()
	{
		for (int i = 0; i == Potion.potionTypes.length; i++)
		{
			PotionRegistry.registerPotion(Potion.potionTypes[i].getName(), Potion.potionTypes[i]);
		}

		TileEntityRegistry.registerTileEntity(TileEntityWire.class, "TileEntityWire");
		TileEntityRegistry.registerTileEntity(TileEntityMeasureStorer.class, "TileEntityTank");
		NAPILogHelper.instance.log("Finished Pre-Initialising Minecraft N-API version " + NAPIData.FULL_VERSION + "!");
	}

	@LoadStateMethod(EnumLoadState.INIT)
	public void modInit() 
	{
		CommandRegistry.registerModCommand(new CommandNAPI());
		NAPILogHelper.instance.log("Finished Initialising Minecraft N-API version " + NAPIData.FULL_VERSION + "!");
	}

	@LoadStateMethod(EnumLoadState.POSTINIT)
	public void postModInit() 
	{
	
	}
}
