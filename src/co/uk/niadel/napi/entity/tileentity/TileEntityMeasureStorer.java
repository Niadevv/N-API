package co.uk.niadel.napi.entity.tileentity;

import co.uk.niadel.napi.common.block.BlockHasMeasure;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMeasureStorer extends TileEntity
{
	public BlockHasMeasure blockMeasureStorer;
	
	/**
	 * The amount of liquid stored in the tank.
	 */
	public long measureValue;
	
	public TileEntityMeasureStorer(BlockHasMeasure blockStorer)
	{
		this.blockMeasureStorer = blockStorer;
		this.measureValue = blockStorer.measure.getMeasures()[0].getValue();
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

	public void drain(long valueToDrainBy)
	{
		this.measureValue -= valueToDrainBy;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if (!this.worldObj.isClient)
		{
			this.blockMeasureStorer.measure.getMeasures()[0].setValue(this.measureValue);
		}
	}
}
