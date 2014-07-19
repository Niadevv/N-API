package co.uk.niadel.mpi.measuresmpi;

import net.minecraft.block.material.Material;
import co.uk.niadel.mpi.common.block.BlockHasFluid;
import co.uk.niadel.mpi.entity.tileentity.TileEntityTank;
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
	public TileEntityTank tankTE;
	
	private BlockTank(Material material, Measure[] measures, EnumLiquidTypes liquidType)
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
		this(material, new Measure[] {new Measure(measureName, maxCapacity)}, liquidType);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		this.tankTE = new TileEntityTank(this);
		return this.tankTE;
	}
}
