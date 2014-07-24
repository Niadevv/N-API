package co.uk.niadel.mpi.annotations;

import java.lang.annotation.*;

/**
 * Contains annotations to mark your methods to better organise it over versions. Other than
 * that, there's no real purpose to this.
 * @author Niadel
 *
 */
public final class VersionMarkingAnnotations
{
	/**
	 * Marks a feature as an test feature.
	 */
	@Retention(RetentionPolicy.SOURCE)
	@Documented
	public @interface TestFeature
	{
		boolean stable() default false;
		
		/**
		 * First mod version this feature appeared.
		 */
		String firstAppearance();
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
