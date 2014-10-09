package co.uk.niadel.napi.measuresapi;

import co.uk.niadel.napi.annotations.DocumentationAnnotations;

@DocumentationAnnotations.Experimental(stable = false, firstAppearance = "1.0")
/**
 * Base for things like EU, RF, etc. Separate from ModFluidMeasure as that has to deal with liquid types and this doesn't.
 * @author Niadel
 *
 */
public class ModEnergyMeasure extends ModMeasureBase 
{
	/**
	 * How much things that contain this value will have their value incremented by.
	 */
	protected long containerFillByValue = 1;

	public ModEnergyMeasure(Measure[] measures)
	{
		super(measures);
	}

	/**
	 * One of the constructors.
	 * @param measures The measures that are part of this measure.
	 * @param containerFillByValue How much a container using this measure will be incremented on it's .measure.incrementMeasure() method.
	 */
	public ModEnergyMeasure(Measure[] measures, int containerFillByValue)
	{
		this(measures);
		this.containerFillByValue = containerFillByValue;
	}

	public long getContainerFillByValue()
	{
		return this.containerFillByValue;
	}

	public void setContainerFillByValue(long newValue)
	{
		this.containerFillByValue = newValue;
	}

	public final boolean isLiquidMeasure()
	{
		return false;
	}
}
