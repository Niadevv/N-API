package co.uk.niadel.napi.measuresapi.blocks;

import co.uk.niadel.napi.measuresapi.ModMeasureBase;
import net.minecraft.block.material.Material;
import co.uk.niadel.napi.measuresapi.EnumLiquidTypes;
import co.uk.niadel.napi.measuresapi.ModFluidMeasure;

/**
 * Base for blocks that have a liquid value, using the Measures MPI. Can be used for Fluid Tanks. This is also a reference for other
 * mod blocks that use the Measures MPI.
 * @author Niadel
 *
 */
public abstract class BlockHasFluid extends BlockHasMeasure
{
	/**
	 * This block's measure.
	 */
	public ModFluidMeasure liquidMeasure;
	
	public BlockHasFluid(Material material, ModMeasureBase measures, EnumLiquidTypes liquidType)
	{
		super(material, measures);
		this.measure = new ModFluidMeasure(measures.getMeasures(), liquidType);
	}
	
	/**
	 * Effectively useless, but there in case the other constructor is already handled.
	 * @param material
	 */
	public BlockHasFluid(Material material)
	{
		this(material, null, null);
	}

	/**
	 * Sets this block's measure. Used for blocks that may have different types of energy.
	 * @param measure
	 */
	public void setMeasure(ModFluidMeasure measure)
	{
		this.measure = measure;
	}
	
	/**
	 * Adds to this block's measure amount.
	 * @param addAmount
	 */
	public void addToMeasure(long addAmount)
	{
		this.measure.incrementMeasure(addAmount);
	}
	
	/**
	 * Subtracts from this block's measure amount.
	 * @param minusAmount
	 */
	public void reduceFromMeasure(long minusAmount)
	{
		this.measure.decrementMeasure(minusAmount);
	}
	
	/**
	 * Returns whether or not this block's contents are an energy producer type.
	 * @return
	 */
	public boolean isContentsEnergyProducer()
	{
		return this.measure.isEnergyProducer();
	}
}