package co.uk.niadel.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import co.uk.niadel.api.measuresmpi.Measure;
import co.uk.niadel.api.measuresmpi.ModEnergyMeasure;

/**
 * A block that has some measure of energy, like an energy battery or something.
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
	 * Removes from thie block's measure.
	 * @param amountToDecrease
	 */
	public void decrementEnergyMeasure(long amountToDecrease)
	{
		this.energyMeasure.decrementMeasure(amountToDecrease);
	}
}
