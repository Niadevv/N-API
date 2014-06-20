package co.uk.niadel.mpi.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import co.uk.niadel.mpi.measuresmpi.Measure;
import co.uk.niadel.mpi.measuresmpi.ModEnergyMeasure;

/**
 * A block that has some measure of energy, like an energy battery or something. This is a 
 * base block for blocks that have an Energy measure.
 * @author Niadel
 *
 */
public abstract class BlockHasEnergy extends Block
{
	/**
	 * This block's measure.
	 */
	public ModEnergyMeasure energyMeasure;
	
	public BlockHasEnergy(Material material, Measure[] measures)
	{
		super(material);
		this.energyMeasure = new ModEnergyMeasure(measures);
	}
	
	/**
	 * Adds to this block's measure.
	 * @param amountToIncrease
	 */
	public void addEnergyMeasure(long amountToIncrease)
	{
		this.energyMeasure.incrementMeasure(amountToIncrease);
	}
	
	/**
	 * Removes from the block's measure.
	 * @param amountToDecrease
	 */
	public void decrementEnergyMeasure(long amountToDecrease)
	{
		this.energyMeasure.decrementMeasure(amountToDecrease);
	}
}
