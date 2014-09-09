package co.uk.niadel.napi.forgewrapper.measuresmpi;

import co.uk.niadel.napi.annotations.VersionMarkingAnnotations.NYI;
import co.uk.niadel.napi.common.IConverter;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts Measures from N-API's own Measures MPI into Forge's equivalent.
 */
@NYI(firstPresence = "0.0", plannedCompletion = "When the Measures MPI is in an appropriate state.")
public class MeasureConverter implements IConverter
{
	public static List<Fluid> convertedMeasuresFluids = new ArrayList<>();

	/**
	 * Not currently used as the Measures MPI is not really in an appropriate state of use for this.
	 */
	@Override
	public void convert()
	{
		/*
		for (ModMeasureBase measure : MeasureRegistry.getMeasures())
		{
			if (measure.isLiquidMeasure())
			{
				for (Measure internMeasure : measure.getMeasures())
				{
					Fluid newFluid = new Fluid(internMeasure.getName());

					if (measure.associatedBlock != null)
					{
						newFluid.setBlock(measure.associatedBlock);
					}

					FluidRegistry.registerFluid(newFluid);
					convertedMeasuresFluids.add(newFluid);
				}
			}
		}*/
	}
}
