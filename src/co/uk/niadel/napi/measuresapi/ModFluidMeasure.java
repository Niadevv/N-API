package co.uk.niadel.napi.measuresapi;

import co.uk.niadel.napi.annotations.DocumentationAnnotations;

@DocumentationAnnotations.Experimental(stable = false, firstAppearance = "1.0")
/**
 * The base class for liquids like those seen in BuildCraft
 * @author Niadel
 */
public class ModFluidMeasure extends ModMeasureBase
{
	/**
	 * True if the liquid can be used to produce energy in some form.
	 */
	public boolean isEnergyProducer = false;

	public EnumLiquidTypes type;
	
	/**
	 * Initialises the information of the fluid measure.
	 * 
	 * @param measures The Measures of this measure.
	 * @param type The type of fluid measure this measure is.
	 */
	public ModFluidMeasure(Measure[] measures, EnumLiquidTypes type)
	{
		super(measures);
		this.type = type;

		if (type == EnumLiquidTypes.OIL || type == EnumLiquidTypes.FUEL || type == EnumLiquidTypes.ENERGY_PROVIDER)
		{
			this.isEnergyProducer = true;
		}
	}
	
	/**
	 * Sets whether or not this is an energy producer.
	 * @param newValue The new value to set isEnergyProducer to.
	 */
	public final void setIsEnergyProducer(boolean newValue)
	{
		this.isEnergyProducer = newValue;
	}

	@Override
	public final boolean isEnergyProducer()
	{
		return this.isEnergyProducer;
	}

	@Override
	public boolean isLiquidMeasure()
	{
		return true;
	}
}
