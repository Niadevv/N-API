package co.uk.niadel.napi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
	@Retention(RetentionPolicy.SOURCE)
	@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
	@Documented
	public @interface RecommendedMethod
	{
		public String recommendedAmount() default "HIGH";
	}
	
	/**
	 * Marks a method as being dangerous to use. Usually used if a method can break compatibility between mods.
	 * @author Niadel
	 */
	@Retention(RetentionPolicy.SOURCE)
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
	@Retention(RetentionPolicy.SOURCE)
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
	@Retention(RetentionPolicy.SOURCE)
	@Documented
	public @interface Internal {}
	
	/**
	 * Tells the user that they should super this method in subclasses.
	 * @author Niadel
	 *
	 */
	@Retention(RetentionPolicy.SOURCE)
	@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
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
	@Retention(RetentionPolicy.SOURCE)
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
	 * @deprecated The library system is mainly gone now, and this is a left over thing.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Deprecated
	public @interface Library
	{
		String version();
	}
	
	/**
	 * Like @UnstableMod, but only for Libraries.
	 * @author Niadel
	 * @deprecated Use @UnstableMod instead.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Deprecated
	public @interface UnstableLibrary
	{
		String version();
		String specialMessage() default "This library is likely to change frequently and mods based on this "
				+ "library may break!";
	}
}
