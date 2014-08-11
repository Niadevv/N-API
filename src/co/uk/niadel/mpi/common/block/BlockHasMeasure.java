package co.uk.niadel.mpi.common.block;

import co.uk.niadel.mpi.measuresmpi.ModMeasureBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 */
public abstract class BlockHasMeasure extends Block
{
	public ModMeasureBase measure;

	public BlockHasMeasure(Material material, ModMeasureBase theMeasure)
	{
		super(material);
		this.measure = theMeasure;
	}
}