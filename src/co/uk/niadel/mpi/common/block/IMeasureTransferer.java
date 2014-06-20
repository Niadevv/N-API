package co.uk.niadel.mpi.common.block;

import net.minecraft.block.Block;
import co.uk.niadel.mpi.measuresmpi.ModMeasureBase;

public interface IMeasureTransferer
{
	/**
	 * Called to transfer the measure to the target.
	 * @param target - What you're transferring to.
	 * @param measure - What you're transferring.
	 */
	public void transferMeasure(IMeasureTransferer target, ModMeasureBase measure);
	
	/**
	 * Called when you receive a measure. Or should be anyways.
	 * @param transferer
	 * @param measure
	 */
	public void onReceiveMeasure(IMeasureTransferer transferer, ModMeasureBase measure);
	
	/**
	 * Called to determine whether or not the target is a suitable transfer target.
	 * @param target
	 * @return
	 */
	public boolean canTransferTo(IMeasureTransferer target);
	
	/**
	 * Called to determine whether or not the target Block is a suitable transferer.
	 * @param theBlock
	 * @return
	 */
	public boolean canTransferToBlock(Block theBlock, int direction);
	
	/**
	 * Gets this transferer's mod measure.
	 * @return
	 */
	public ModMeasureBase getMeasure();
}
