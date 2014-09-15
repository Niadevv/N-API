package co.uk.niadel.napi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Deletes fields marked with this if a certain mod exists.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReplacementFor
{
	public String itemInModWithModId();
}
