package co.uk.niadel.mpi.measuresmpi;

import co.uk.niadel.mpi.annotations.VersionMarkingAnnotations.NYI;

import java.util.ArrayList;
import java.util.List;

/**
 * Important if you want your mod to work with Forge mods.
 */
@NYI(firstPresence = "0.0")
public class MeasureRegistry
{
	/**
	 * The registered measures.
	 */
	public static final List<ModMeasureBase> measures = new ArrayList<>();

	/**
	 * Registers a measure.
	 * @param measure The measure
	 */
	public static final void registerMeasure(ModMeasureBase measure)
	{
		measures.add(measure);
	}

	/**
	 * Gets the measures that have been registered.
	 * @return The measures that have been registered.
	 */
	public static final List<ModMeasureBase> getMeasures()
	{
		return measures;
	}
}
