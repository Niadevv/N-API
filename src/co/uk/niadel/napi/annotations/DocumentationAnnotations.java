package co.uk.niadel.napi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations with no actual purpose beyond documentation.
 * 
 * @author Niadel
 */
public final class DocumentationAnnotations
{
	/**
	 * Marks a feature as a test feature.
	 * @author Niadel
	 */
	@Retention(RetentionPolicy.SOURCE)
	@Documented
	public @interface Experimental
	{
		boolean stable() default false;

		/**
		 * First mod version this feature appeared.
		 */
		String firstAppearance();
	}

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

	/**
	 * Records a feature's first appearance in a stable release version.
	 * @author Niadel
	 *
	 */
	@Retention(RetentionPolicy.SOURCE)
	@Documented
	public @interface FirstStableRelease
	{
		String firstStableRelease();
	}

	/**
	 * Marks a feature as not being added quite yet.
	 * @author Niadel
	 *
	 */
	@Retention(RetentionPolicy.SOURCE)
	@Documented
	public @interface NYI
	{
		String firstPresence();
		String plannedCompletion() default "Soon!";
	}

	/**
	 * Marks a feature as being not finished.
	 * @author Niadel
	 *
	 */
	@Retention(RetentionPolicy.SOURCE)
	@Documented
	public @interface NotCompleted
	{
		String firstPresence();
		String plannedCompletion() default "Soon!";
	}
}
