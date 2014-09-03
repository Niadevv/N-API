package co.uk.niadel.napi.annotations;

import java.lang.annotation.*;

/**
 * Marker for ASM transformers. This will automatically register them without any effort on your end.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface ASMTransformer
{
}
