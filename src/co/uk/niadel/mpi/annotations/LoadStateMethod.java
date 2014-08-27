package co.uk.niadel.mpi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Allows for a mod method that's important to be called something other than the default
 * preInit(), init(), and postInit(). loadPoint must be one of either preInit, init, or
 * postInit. ONLY HAVE ONE METHOD OF THE SPECIFIC LOAD POINT MARKED WITH THIS PER CLASS!
 * @author Niadel
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoadStateMethod
{
	EnumLoadState value();
}
