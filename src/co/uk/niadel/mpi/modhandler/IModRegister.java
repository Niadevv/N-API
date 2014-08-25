package co.uk.niadel.mpi.modhandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Interface for basic mod registers that don't need to fuss with dependencies or ASM stuff. YOU MUST IMPLEMENT EITHER THIS
 * OR IAdvancedModRegister FOR YOUR MOD TO BE LOADED!
 * @author Niadel
 *
 */
@Deprecated
public interface IModRegister
{
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
	 * Called after the register is loaded. This is where you do things that need other mods to have gone through their modInit stages.
	 */
	public void postModInit();
	
	/**
	 * Returns the version of the mod.
	 * @return This mod's version.
	 */
	public String getVersion();
	
	/**
	 * Returns the mod id of this mod.
	 * @return This mod's modid.
	 */
	public String getModId();
}
