package co.uk.niadel.napi.measuresapi.blocks;

import co.uk.niadel.napi.entity.tileentity.TileEntityMeasureStorer;
import co.uk.niadel.napi.measuresapi.EnumLiquidTypes;
import co.uk.niadel.napi.measuresapi.Measure;
import co.uk.niadel.napi.measuresapi.ModFluidMeasure;
import net.minecraft.block.material.Material;
import co.uk.niadel.napi.measuresapi.blocks.BlockHasFluid;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Block that contains a liquid measure (just one, if you want more, you'll have to do it yourself for the time being).
 * @author Niadel
 *
 */
public class BlockTank extends BlockHasFluid implements ITileEntityProvider
{
	public TileEntityMeasureStorer tankTE;
	
	private BlockTank(Material material, ModFluidMeasure measures, EnumLiquidTypes liquidType)
	{
		super(material, measures, liquidType);
	}
	
	public BlockTank(Material material, int maxCapacity)
	{
		this(material, maxCapacity, "tankMeasure");
	}
	
	public BlockTank(Material material, int maxCapacity, String measureName)
	{
		this(material, maxCapacity, measureName, EnumLiquidTypes.UNDEFINED);
	}
	
	public BlockTank(Material material, int maxCapacity, String measureName, EnumLiquidTypes liquidType)
	{
		this(material, new ModFluidMeasure(new Measure[] {new Measure(measureName, maxCapacity)}, liquidType), liquidType);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		this.tankTE = new TileEntityMeasureStorer(this);
		return this.tankTE;
	}

	/**
	 * 'Fills' this block.
	 * @param fillValue The amount to fill this block by.
	 */
	public void fill(long fillValue)
	{
		if (this.canFill() && this.tankTE != null)
		{
			this.tankTE.fill(fillValue);
		}
	}

	/**
	 * 'Drains' this block.
	 * @param drainValue How much to drain this block.
	 */
	public void drain(long drainValue)
	{
		if (this.canDrain() && this.tankTE != null)
		{
			this.tankTE.drain(drainValue);
		}
	}

	/**
	 * Gets whether or not this block can be filled.
	 * @return Whether or not this block can be filled.
	 */
	public boolean canFill()
	{
		return true;
	}

	/**
	 * Gets whether or not this block can be drained.
	 * @return Whether or not this block can be filled.
	 */
	public boolean canDrain()
	{
		return true;
	}
}
