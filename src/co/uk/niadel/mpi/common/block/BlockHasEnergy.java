package co.uk.niadel.mpi.common.block;

import co.uk.niadel.mpi.measuresmpi.ModMeasureBase;
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
public abstract class BlockHasEnergy extends BlockHasMeasure
{
	public BlockHasEnergy(Material material, ModMeasureBase measure)
	{
		super(material, measure);
	}
	
	/**
	 * Adds to this block's measure.
	 * @param amountToIncrease
	 */
	public void addEnergyMeasure(long amountToIncrease)
	{
		this.measure.incrementMeasure(amountToIncrease);
	}
	
	/**
	 * Removes from the block's measure.
	 * @param amountToDecrease
	 */
	public void decrementEnergyMeasure(long amountToDecrease)
	{
		this.measure.decrementMeasure(amountToDecrease);
	}
}
