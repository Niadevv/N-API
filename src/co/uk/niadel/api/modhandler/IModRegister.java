package co.uk.niadel.api.modhandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * All registers must implement this or else your mod will not load.
 * @author Niadel
 *
 */
public interface IModRegister
{
	public Set<String> dependencies = new HashSet<>();
	
	/**
	 * The map that contains all libraries required by the mod - The first value
	 * is the library's modid, and the second is the mod's minimum required version of that
	 * library.
	 */
	public Map<String, String> libraryDependencies = new HashMap<>();
	
	/**
	 * A mod's unique identifier.
	 */
	public String modId = "";
	
	/**
	 * The mod's version. You more or less have to specify this, or at the very least it is 
	 * very recommended.
	 */
	public String version = "";
	
	/**
	 * Where you register transformers. This is called BEFORE everything else, in Bootstrap.
	 */
	public void registerTransformers();
	
	/**
	 * Called before modInit(). Register events and event handlers here.
	 */
	public void preModInit();
	
	/**
	 * Called when the register is initialised. Register any blocks and items here and call
	 * important methods here.
	 */
	public void modInit();
	
	/**
	 * Called after the register is loaded, where I recommend you load MPI's for your mod.
	 */
	public void postModInit();
	
	/**
	 * Where you add modids to the Set dependencies. 
	 */
	public void addRequiredMods();
	
	/**
	 * Where you add required mod libraries.
	 */
	public void addRequiredLibraries();
}
