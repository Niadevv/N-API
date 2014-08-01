package co.uk.niadel.mpi.modhandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Interface for advanced mod registers that need access to things like dependencies of ASM.
 */
public interface IAdvancedModRegister extends IModRegister
{
	/**
	 * Non-Library dependencies. This is for mods that are extensions of another, like Thaumic Tinkerer is an extension
	 * of Thaumcraft.
	 *
	 * Example:
	 * 		dependencies.add(NModLoader.getModByModId("NIADEL_n_api"));
	 * 		Adds (unecessarily) the N-API register as a dependency.
	 */
	public Set<IModRegister> dependencies = new HashSet<>();

	/**
	 * The map that contains all libraries required by the mod - The first value
	 * is the library's modid, and the second is the mod's minimum required version of that
	 * library. Get the IModRegister by the same method you get it for when you add a dependency
	 * and ensure the version is actually a version that will exist (see the library's IModRegister file).
	 */
	public Map<IModRegister, String> libraryDependencies = new HashMap<>();

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

	/**
	 * Where you add annotation handlers.
	 */
	public void registerAnnotationHandlers();
}
