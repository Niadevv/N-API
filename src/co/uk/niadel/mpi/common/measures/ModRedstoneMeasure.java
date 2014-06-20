package co.uk.niadel.mpi.common.measures;

import co.uk.niadel.mpi.common.block.IRedstoneWire;
import co.uk.niadel.mpi.measuresmpi.Measure;
import co.uk.niadel.mpi.measuresmpi.ModEnergyMeasure;

/**
 * Used as a universal mod redstone measure.
 * @author Niadel
 *
 */
public class ModRedstoneMeasure extends ModEnergyMeasure
{
	public ModRedstoneMeasure(IRedstoneWire wireBlock)
	{
		super(new Measure[] {new Measure(wireBlock.getRedstoneLevel(), "redstone", wireBlock.getMaxRedstoneLevel())});
	}
}
