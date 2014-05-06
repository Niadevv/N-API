package co.uk.niadel.api.annotations;

import java.lang.annotation.*;

/**
 * Contains annotations to mark your methods to better organise it over versions. Other than
 * that, there's no real purpose to this.
 * @author Niadel
 *
 */
public final class VersionMarkingAnnotations
{
	@Retention(value = RetentionPolicy.SOURCE)
	@Documented
	/**
	 * Marks a feature as an test feature.
	 */
	public @interface TestFeature
	{
		boolean stable() default false;
		
		/**
		 * First mod version this feature appeared.
		 */
		String firstAppearance();
	}
	
	@Retention(value = RetentionPolicy.SOURCE)
	@Documented
	/**
	 * Records a feature's first appearance in a stable release version.
	 * @author Niadel
	 *
	 */
	public @interface FirstStableRelease
	{
		String firstStableRelease();
	}
	
	@Retention(value = RetentionPolicy.SOURCE)
	@Documented
	/**
	 * Marks a feature as not being added quite yet.
	 * @author Niadel
	 *
	 */
	public @interface NYI
	{
		String firstPresence();
		String plannedCompletion() default "Soon!";
	}
	
	@Retention(value = RetentionPolicy.SOURCE)
	@Documented
	/**
	 * Marks a feature as being not finished.
	 * @author Niadel
	 *
	 */
	public @interface NotCompleted
	{
		String firstPresence();
		String plannedCompletion() default "Soon!";
	}
}
