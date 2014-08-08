package co.uk.niadel.mpi.measuresmpi;

import java.util.ArrayList;
import java.util.List;

/**
 * Important if you want your mod to work with Forge mods.
 */
public class MeasureRegistry
{
	public static final List<ModMeasureBase> measures = new ArrayList<>();

	public static final void registerMeasure(ModMeasureBase measure)
	{
		measures.add(measure);
	}

	public static final List<ModMeasureBase> getMeasures()
	{
		return measures;
	}
}
