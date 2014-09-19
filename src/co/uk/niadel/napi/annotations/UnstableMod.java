package co.uk.niadel.napi.annotations;

import java.lang.annotation.*;

/**
 * Marks a mod as being an unstable mod.
 * @author Niadel
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface UnstableMod
{
	String warningMessage() default "Please do not use this on any of your important worlds, "
			+ "as this mod could change drastically and break all the things. If you must, make backups!";
}
