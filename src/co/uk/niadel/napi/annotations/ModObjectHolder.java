package co.uk.niadel.napi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Somewhat analogous to Forge's @ObjectHolder. However, internally it reuses the @Immutable system.
 * Note that classes with this field CANNOT have methods (well they can, they'll just be removed).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModObjectHolder
{
}
