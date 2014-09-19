package co.uk.niadel.napi.annotations;

import java.lang.annotation.*;

/**
 * Deletes fields marked with this if a certain mod exists (the mod with the modid specified in itemInModWithModId()).
 *
 * @author Niadel
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface ReplacementFor
{
	public String itemInModWithModId();
}
