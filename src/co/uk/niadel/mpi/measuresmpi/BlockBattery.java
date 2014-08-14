package co.uk.niadel.mpi.measuresmpi;

import co.uk.niadel.mpi.common.block.BlockHasEnergy;
import net.minecraft.block.material.Material;

/**
 * Block that contains a ModEnergyMeasure value, like BlockTank to ModLiquidMeasure.
 *
 * @author Niadel
 */
public class BlockBattery extends BlockHasEnergy
{
	/**
	 * How much the measure recieved in .charge() is reduced by before the measure is actually incremented.
	 */
	public int resistance;

	public BlockBattery(Material material, ModEnergyMeasure measure, int resistance)
	{
		super(material, measure);
		this.resistance = resistance;
	}

	public BlockBattery(Material material, ModEnergyMeasure measure)
	{
		this(material, measure, 0);
	}

	/**
	 * 'Charges' this block.
	 * @param theCharge How much to charge this battery by.
	 */
	public void charge(long theCharge)
	{
		this.measure.incrementMeasure(theCharge - this.resistance);
	}

	/**
	 * 'Drains' this block.
	 * @param drainAmount The amount to drain this block by.
	 */
	public void drain(long drainAmount)
	{
		this.measure.decrementMeasure(drainAmount);
	}
}
