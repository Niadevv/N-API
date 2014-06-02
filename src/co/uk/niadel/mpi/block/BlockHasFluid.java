package co.uk.niadel.mpi.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import co.uk.niadel.mpi.measuresmpi.EnumLiquidTypes;
import co.uk.niadel.mpi.measuresmpi.Measure;
import co.uk.niadel.mpi.measuresmpi.ModFluidMeasure;

/**
 * Base for blocks that have a liquid value, using the Measures MPI. Can be used for Fluid Tanks.This is a reference for other mod 
 * blocks that use the Measures MPI.
 * @author Niadel
 *
 */
public abstract class BlockHasFluid extends Block
{
	/**
	 * This block's measure.
	 */
	public ModFluidMeasure liquidMeasure;
	
	public BlockHasFluid(Material material, Measure[] measures, EnumLiquidTypes liquidType)
	{
		super(material);
		this.liquidMeasure = new ModFluidMeasure(measures, liquidType);
	}
	
	/**
	 * Sets this block's measure. Used for blocks that may have different types of energy.
	 * @param measure
	 */
	public void setMeasure(ModFluidMeasure measure)
	{
		this.liquidMeasure = measure;
	}
	
	/**
	 * Adds to this block's measure amount.
	 * @param addAmount
	 */
	public void addToMeasure(long addAmount)
	{
		this.liquidMeasure.incrementMeasure(addAmount);
	}
	
	/**
	 * Subtracts from this block's measure amount.
	 * @param minusAmount
	 */
	public void reduceFromMeasure(long minusAmount)
	{
		this.liquidMeasure.decrementMeasure(minusAmount);
	}
	
	/**
	 * Returns whether or not this block's contents are an energy producer type.
	 * @return
	 */
	public boolean isContentsEnergyProducer()
	{
		return this.liquidMeasure.isEnergyProducer();
	}
}