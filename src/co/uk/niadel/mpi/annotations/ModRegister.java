package co.uk.niadel.mpi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Classes that implement this will be found via the loader and loaded. Like FML's @Mod, only much simpler and has less options.
 *
 * @author Niadel
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ModRegister
{
	String modId();
	String version();
	boolean isLibrary() default false;
	String[] dependencies() default {};
}