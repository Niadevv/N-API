package co.uk.niadel.napi.measuresapi.blocks;

import co.uk.niadel.napi.measuresapi.ModMeasureBase;
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
