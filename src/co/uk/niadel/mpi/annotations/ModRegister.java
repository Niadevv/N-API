package co.uk.niadel.mpi.annotations;

import java.lang.annotation.*;

/**
 * Classes that implement this will be found via the loader and loaded. Like FML's @Mod, only much simpler and has less options.
 *
 * @author Niadel
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ModRegister
{
	/**
	 * Returns the unique id of this mod. The general naming convention is your name in uppercase followed by the mods name in all lower,
	 * separated by underscores.
	 * @return The unique id of this mod.
	 */
	String modId();

	/**
	 * Returns the version of this mod. Should follow Minecraft's versioning conventions.
	 * @return The version of this mod. Should follow Minecraft's versioning conventions.
	 */
	String version();

	/**
	 * Whether or not this mod is a Library.
	 * @return Whether or not this mod is a Library.
	 */
	boolean isLibrary() default false;

	/**
	 * Returns a list of mod ids of mods this mod must have to function.
	 * @return A list of mod ids of mods this mod must have to function.
	 */
	String[] dependencies() default {};
}