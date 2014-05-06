package co.uk.niadel.api.annotations;

import java.lang.annotation.*;

/**
 * Useful for mods with MPIs of their own but still mainly use
 * N-API.
 * 
 * @author Niadel
 */
public final class MPIAnnotations 
{
	
	//Most of these are documentation Annotations. Only 3 are currently used at runtime.
	@Retention(value = RetentionPolicy.SOURCE)
	@Documented
	/**
	 * Marks a method as being the recommended method to use.
	 * @author Niadel
	 */
	public @interface RecommendedMethod
	{
		
	}
	
	@Retention(value = RetentionPolicy.SOURCE)
	@Documented
	/**
	 * Tells readers that you still want to add something.
	 * @author Niadel
	 */
	public @interface TODO
	{
		String todo() default "Undefined";
	}
	
	@Retention(value = RetentionPolicy.SOURCE)
	@Documented
	/**
	 * Marks a feature as not being permanent.
	 * @author Niadel
	 *
	 */
	public @interface Temprorary
	{
		String versionToBeRemoved() default "Soon!";
	}
	
	//BELOW: SPECIAL ANNOTATIONS
	//These are used to add extra functionality to ModRegisters.
	@Retention(value = RetentionPolicy.RUNTIME)
	@Documented
	/**
	 * Marks a mod as being a library. Only used if the mod is a library that doesn't change
	 * anything or just adds methods and classes to interact with other mods. This is the only
	 * annotation used at runtime, unlike the others which (currently) are only
	 * for marking the mod's code.
	 * @author Niadel
	 *
	 */
	public @interface Library
	{
		String version();
	}
	
	@Retention(value = RetentionPolicy.RUNTIME)
	@Documented
	/**
	 * Marks a mod as being an unstable mod.
	 * @author Niadel
	 *
	 */
	public @interface UnstableMod
	{
		String specialMessage() default "Please do not use this on any of your important worlds, "
				+ "as this mod could change drastically and break everything";
	}
	
	/**
	 * Like @UnstableMod, but only for Libraries.
	 * @author Niadel
	 *
	 */
	public @interface UnstableLibrary
	{
		String specialMessage() default "This library is likely to change frequently and mods based on this "
				+ "library may break!";
	}
}
