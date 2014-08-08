package co.uk.niadel.mpi.forgewrapper.measuresmpi;

import co.uk.niadel.mpi.common.IConverter;
import co.uk.niadel.mpi.measuresmpi.Measure;
import co.uk.niadel.mpi.measuresmpi.MeasureRegistry;
import co.uk.niadel.mpi.measuresmpi.ModMeasureBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts Measures from N-API's own Measures MPI into Forge's equivalent.
 */
public class MeasureConverter implements IConverter
{
	public static List<Fluid> convertedMeasuresFluids = new ArrayList<>();

	public void convert()
	{
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
		}
	}
}
