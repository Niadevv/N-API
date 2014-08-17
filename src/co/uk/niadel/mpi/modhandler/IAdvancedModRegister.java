package co.uk.niadel.mpi.modhandler;

/**
 * Interface for advanced mod registers that need access to things like dependencies of ASM.
 */
public interface IAdvancedModRegister extends IModRegister
{
	/**
	 * Where you register transformers. This is called BEFORE everything else, in Bootstrap. DO NOT USE TO REGISTER BLOCKS!
	 */
	public void registerTransformers();

	/**
	 * Where you add dependencies with DependenciesRegistry.
	 *
	 * Example:
	 *
	 * DependenciesRegistry.addDependency(this, "NIADEL_example_mod");
	 */
	public void registerDependencies();

	/**
	 * Where you add annotation handlers.
	 */
	public void registerEventHandlers();
}
