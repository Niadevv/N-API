package co.uk.niadel.napi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks an annotation as being internal and not to use.
 * @author Niadel
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Internal
{}
