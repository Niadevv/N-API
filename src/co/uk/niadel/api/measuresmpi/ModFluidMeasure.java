package co.uk.niadel.api.measuresmpi;

import co.uk.niadel.api.annotations.VersionMarkingAnnotations;
import co.uk.niadel.api.annotations.VersionMarkingAnnotations.TestFeature;

@TestFeature(stable = false, firstAppearance = "1.0")
/**
 * The base class for liquids like those seen in BuildCraft
 * @author Niadel
 */
public class ModFluidMeasure extends ModMeasureBase
{
	/**
	 * True if the liquid can be used to produce energy in some form.
	 */
	protected boolean isEnergyProducer = false;	
	
	/**
	 * Initialises the information of the fluid measure.
	 * 
	 * @param measures
	 * @param type
	 */
	public ModFluidMeasure(Measure[] measures, EnumLiquidTypes type)
	{
		super(measures);
		
		if (type == EnumLiquidTypes.OIL || type == EnumLiquidTypes.FUEL || type == EnumLiquidTypes.ENERGY_PROVIDER)
		{
			this.isEnergyProducer = true;
		}
	}
	
	public final void setIsEnergyProducer(boolean newValue)
	{
		this.isEnergyProducer = newValue;
	}
	
	public final boolean isEnergyProducer()
	{
		return this.isEnergyProducer;
	}
}
