package co.uk.niadel.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import co.uk.niadel.api.measuresmpi.Measure;
import co.uk.niadel.api.measuresmpi.ModEnergyMeasure;

public abstract class BlockHasEnergy extends Block
{
	public ModEnergyMeasure energyMeasure;
	
	public BlockHasEnergy(Material material, Measure[] measures)
	{
		super(material);
		this.energyMeasure = new ModEnergyMeasure(measures);
	}
	
	public void addEnergyMeasure(long amountToIncrease)
	{
		this.energyMeasure.incrementMeasure(amountToIncrease);
	}
	
	public void decrementEnergyMeasure(long amountToDecrease)
	{
		this.energyMeasure.decrementMeasure(amountToDecrease);
	}
}
