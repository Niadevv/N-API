package co.uk.niadel.napi.common.measures;

import co.uk.niadel.napi.common.block.IRedstoneWire;
import co.uk.niadel.napi.measuresmpi.Measure;
import co.uk.niadel.napi.measuresmpi.ModEnergyMeasure;

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
