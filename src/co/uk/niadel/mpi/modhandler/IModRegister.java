package co.uk.niadel.mpi.modhandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * All registers must implement this or else your mod will not load and stuff will break.
 * @author Niadel
 *
 */
public interface IModRegister
{
	/**
	 * Non-Library dependencies. This is for mods that are extensions of another, like Thaumic Tinkerer is an extension
	 * of Thaumcraft.
	 */
	public Set<IModRegister> dependencies = new HashSet<>();
	
	/**
	 * The map that contains all libraries required by the mod - The first value
	 * is the library's modid, and the second is the mod's minimum required version of that
	 * library.
	 */
	public Map<IModRegister, String> libraryDependencies = new HashMap<>();
	
	/**
	 * Whether or not this Mod is using @ModRegister to define it's variables.
	 */
	public boolean isUsingAnnotation = false;
	
	/**
	 * Called before modInit(). Register event handlers, blocks and here.
	 */
	public void preModInit();
	
	/**
	 * Called when the register is initialised. Call important methods here.
	 */
	public void modInit();
	
	/**
	 * Called after the register is loaded, where I recommend you load MPI's for your mod.
	 */
	public void postModInit();
	
	/**
	 * Returns the version of the mod.
	 * @return See above.
	 */
	public String getVersion();
	
	/**
	 * Returns the mod id of this mod.
	 * @return See above.
	 */
	public String getModId();
	
	/**
	 * Where you register transformers. This is called BEFORE everything else, in Bootstrap. DO NOT USE TO REGISTER BLOCKS!
	 */
	public void registerTransformers();

	/**
	 * Where you add modids to the Set dependencies. 
	 */
	public void addRequiredMods();
	
	/**
	 * Where you add required mod libraries.
	 */
	public void addRequiredLibraries();
}
