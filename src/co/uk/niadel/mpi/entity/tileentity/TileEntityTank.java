package co.uk.niadel.mpi.entity.tileentity;

import net.minecraft.tileentity.TileEntity;
import co.uk.niadel.mpi.measuresmpi.BlockTank;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityTank extends TileEntity
{
	public BlockTank blockTank;
	
	/**
	 * The amount of liquid stored in the tank.
	 */
	public long measureValue;
	
	public TileEntityTank(BlockTank tank)
	{
		this.blockTank = tank;
		this.measureValue = tank.liquidMeasure.getMeasures()[0].getValue();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.measureValue = tag.getLong("LiquidMeasure");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setLong("LiquidMeasure", this.measureValue);
	}
	
	public void fill(long valueToFillBy)
	{
		this.measureValue += valueToFillBy;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if (!this.worldObj.isClient)
		{
			this.blockTank.liquidMeasure.getMeasures()[0].setValue(this.measureValue);
		}
	}
}
