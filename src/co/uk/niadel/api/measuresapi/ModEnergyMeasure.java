package co.uk.niadel.api.measuresapi;

import co.uk.niadel.api.annotations.VersionMarkingAnnotations.TestFeature;

@TestFeature(stable = false, firstAppearance = "1.0")
/**
 * Base for things like EU, RF, etc.
 * @author Niadel
 *
 */
public class ModEnergyMeasure extends ModMeasureBase 
{
	/**
	 * An array of the measures, lowest at the array index 0.
	 */
	public Measure[] measures;
	
	public ModEnergyMeasure(Measure[] measure)
	{
		super(measure);
	}
}
