package co.uk.niadel.mpi.measuresmpi;

import co.uk.niadel.mpi.annotations.VersionMarkingAnnotations.TestFeature;

@TestFeature(stable = false, firstAppearance = "1.0")
/**
 * Base for things like EU, RF, etc. Separate from ModFluidMeasure as that has to deal with liquid types and this doesn't.
 * @author Niadel
 *
 */
public class ModEnergyMeasure extends ModMeasureBase 
{
	public ModEnergyMeasure(Measure[] measure)
	{
		super(measure);
	}
}
