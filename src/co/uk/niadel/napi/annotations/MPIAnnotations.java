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
}
