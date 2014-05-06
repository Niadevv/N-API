package co.uk.niadel.api.events;

import co.uk.niadel.api.annotations.VersionMarkingAnnotations.TestFeature;

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
	 * The event's data, currently not used.
	 */
	Object[] data = null;
	
	/**
	 * Where to do special stuff - In N-API events, it just calls addData, but you
	 * can do more with it.
	 */
	public void initEvent();
}
