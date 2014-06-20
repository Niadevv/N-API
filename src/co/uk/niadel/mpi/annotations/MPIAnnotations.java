package co.uk.niadel.mpi.annotations;

import java.lang.annotation.*;

/**
 * Useful for mods with MPIs of their own but still mainly use
 * N-API. Most of these are documentation Annotations. Only a handful are currently used at runtime.
 * 
 * @author Niadel
 */
public final class MPIAnnotations 
{
	/**
	 * Marks a method as being the recommended method to use.
	 * @author Niadel
	 */
	@Retention(value = RetentionPolicy.SOURCE)
	@Documented
	public @interface RecommendedMethod
	{
		public String recommendedAmount() default "HIGH";
	}
	
	/**
	 * Marks a method as being dangerous to use. Usually used if a method can break compatibility between mods.
	 * @author Niadel
	 */
	@Retention(value = RetentionPolicy.SOURCE)
	@Documented
	public @interface Dangerous
	{
		public String danger() default "HIGH";
		public String reason() default "None";
	}
	
	/**
	 * Tells readers that you still want to add something.
	 * @author Niadel
	 */
	@Retention(value = RetentionPolicy.SOURCE)
	@Documented
	public @interface TODO
	{
		String todo() default "Undefined";
	}
	
	/**
	 * Marks an annotation as being internal and not to use.
	 * @author Niadel
	 *
	 */
	@Retention(value = RetentionPolicy.SOURCE)
	@Documented
	public @interface Internal {}
	
	@Retention(value = RetentionPolicy.SOURCE)
	@Target(value = ElementType.METHOD)
	@Documented
	public @interface ShouldSuperInSubclasses
	{
		String priority() default "High";
	}
	
	/**
	 * Marks a feature as not being permanent.
	 * @author Niadel
	 *
	 */
	@Retention(value = RetentionPolicy.SOURCE)
	@Documented
	public @interface Temprorary
	{
		String versionToBeRemoved() default "Soon!";
	}
	
//#############################################################################################################################
//BELOW: SPECIAL ANNOTATIONS
//These are used to add extra functionality to ModRegisters.
//#############################################################################################################################
	
	/**
	 * Marks a mod as being a library. Only used if the mod is a library that doesn't change
	 * anything or just adds methods and classes to interact with other mods.
	 * @author Niadel
	 *
	 */
	@Retention(value = RetentionPolicy.RUNTIME)
	@Documented
	public @interface Library
	{
		String version();
	}
	
	/**
	 * Marks a mod as being an unstable mod.
	 * @author Niadel
	 *
	 */
	@Retention(value = RetentionPolicy.RUNTIME)
	@Documented
	public @interface UnstableMod
	{
		String specialMessage() default "Please do not use this on any of your important worlds, "
				+ "as this mod could change drastically and break everything";
	}
	
	/**
	 * Like @UnstableMod, but only for Libraries.
	 * @author Niadel
	 */
	@Retention(value = RetentionPolicy.RUNTIME)
	@Documented
	public @interface UnstableLibrary
	{
		String specialMessage() default "This library is likely to change frequently and mods based on this "
				+ "library may break!";
	}
	
	/**
	 * Somewhat analagous to Forge's @Mod annotation, only optional, and is just a shortcut rather than the fixed way
	 * of defining ModRegisters.
	 * @author Niadel
	 *
	 */
	@Retention(value = RetentionPolicy.RUNTIME)
	public @interface ModRegister
	{
		String version();
		String modId();
	}
}
