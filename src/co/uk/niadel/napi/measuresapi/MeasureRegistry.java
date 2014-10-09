package co.uk.niadel.napi.measuresapi;

import co.uk.niadel.napi.annotations.DocumentationAnnotations.NYI;

import java.util.ArrayList;
import java.util.List;

/**
 * Currently only used internally. May be removed.
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
