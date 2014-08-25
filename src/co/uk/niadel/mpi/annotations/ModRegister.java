package co.uk.niadel.mpi.annotations;

import co.uk.niadel.mpi.asm.IASMTransformer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Classes that implement this will be found via the loader and loaded. Like @Mod, only much simpler and has less options.
 *
 * @author Niadel
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ModRegister
{
	String modId();
	String version();
	boolean isLibrary() default false;
}