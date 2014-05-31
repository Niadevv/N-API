package co.uk.niadel.mpi.events;

import co.uk.niadel.mpi.annotations.VersionMarkingAnnotations.TestFeature;

@TestFeature(stable = false, firstAppearance = "1.0")
/**
 * There used to be a registering system that required this. It was very broken and didn't
 * work.
 * @author Niadel
 *
 */
public interface IEvent 
{
	/**
	 * Gets the event's name.
	 * @return
	 */
	public String getName();
}
