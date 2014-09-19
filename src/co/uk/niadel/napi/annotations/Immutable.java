package co.uk.niadel.napi.annotations;

import java.lang.annotation.*;

/**
 * Annotating a field with this makes this field impossible to edit via reflection or ASM. Note that it won't affect calls to
 * Map.put/List.add/remove etc. There are maps and lists to solve that, though.
 *
 * @author Niadel
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Immutable
{
}
