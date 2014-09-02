package co.uk.niadel.mpi.annotations;

import java.lang.annotation.*;

/**
 * Marks a field as containing an Event Handler so it can be auto-registered. Not to be confused with
 * FML's @EventHandler which is put on the FML event handlers.
 *
 * @author Niadel
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface EventHandler
{
}
