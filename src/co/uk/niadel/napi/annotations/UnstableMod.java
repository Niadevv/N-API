package co.uk.niadel.napi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks a mod as being an unstable mod.
 * @author Niadel
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UnstableMod
{
	String warningMessage() default "Please do not use this on any of your important worlds, "
			+ "as this mod could change drastically and break everything. If you must, make backups!";
}
