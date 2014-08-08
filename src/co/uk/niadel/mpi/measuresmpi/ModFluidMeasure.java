package co.uk.niadel.mpi.measuresmpi;

import co.uk.niadel.mpi.annotations.VersionMarkingAnnotations;
import co.uk.niadel.mpi.annotations.VersionMarkingAnnotations.TestFeature;

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
	public boolean isEnergyProducer = false;	
	
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
	
	/**
	 * Sets whetehr or not this is an energy producer.
	 * @param newValue
	 */
	public final void setIsEnergyProducer(boolean newValue)
	{
		this.isEnergyProducer = newValue;
	}
	
	public final boolean isEnergyProducer()
	{
		return this.isEnergyProducer;
	}

	public final boolean isLiquidMeasure()
	{
		return true;
	}
}
