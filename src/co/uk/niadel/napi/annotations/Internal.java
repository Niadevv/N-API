package co.uk.niadel.napi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks an annotation as being internal and not to use. Useful if you want to use a field only in your packages but want
 * a field in package block to be used in package render.
 * @author Niadel
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Internal
{
	/**
	 * The package that owns this and that calls to this outside of it are removed. N-API's would be co.uk.niadel.napi at time
	 * of writing.
	 */
	public String owningPackage() default "";

	/**
	 * If false, it removes calls/usages outside of owningPackage. Otherwise, it's documentation only.
	 * @return Whether or not this is documentation only.
	 */
	public boolean documentationOnly() default true;
}
