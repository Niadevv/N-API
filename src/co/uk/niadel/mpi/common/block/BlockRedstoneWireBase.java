package co.uk.niadel.mpi.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import co.uk.niadel.mpi.common.measures.ModRedstoneMeasure;
import co.uk.niadel.mpi.util.BlockDirectionsMod;

/**
 * Base for mod redstone wire. Not the same as vanilla's system but is more flexible, albeit
 * laggier in bigger redstone contraptions.
 * @author Niadel
 *
 */
public class BlockRedstoneWireBase extends BlockWireBase implements BlockDirectionsMod, IRedstoneWire
{
	/**
	 * A static INSTANCE to allow for the constructor to work.
	 */
	public static BlockRedstoneWireBase instance = new BlockRedstoneWireBase();
	
	public BlockRedstoneWireBase()
	{
		super(Material.circuits, new ModRedstoneMeasure(instance));
	}
	
	@Override
	public boolean canTransferToBlock(Block block, int direction)
	{
		if ((!(direction == UP) && !(direction == DOWN) && direction < 6) || block instanceof IRedstoneWire || block instanceof BlockRedstoneWire)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public long getRedstoneLevel()
	{
		return this.theMeasure.getMeasure("redstone").getValue();
	}
	
	@Override
	public long getMaxRedstoneLevel()
	{
		return 15;
	}
	
	@Override
	public void onConnect(Block block, int direction)
	{
		if (block instanceof IRedstoneWire)
		{
			IRedstoneWire theBlock = (IRedstoneWire) block;
			theBlock.power(this.getRedstoneLevel() - 1);
		}
		else if (block instanceof BlockRedstoneWire)
		{
			BlockRedstoneWire theBlock = (BlockRedstoneWire) block;
			int[] coordsArray = this.getCoordsFromDirection(direction);
			
			if (this.theMeasure.getMeasure("redstone").getValue() >= 15)
			{
				//Sets the redstone wire block's redstone level.
				this.wireTileEntity.getWorldObj().setBlockMetadataWithNotify(coordsArray[0], coordsArray[1], coordsArray[2], theBlock.getDamageValue(this.wireTileEntity.getWorld(), coordsArray[0], coordsArray[1], coordsArray[2]) - 1, 3);
			}
			else
			{
				//This block's redstone is too high, set the redstone level to the highest available level of 15. Safety check for metadata values u.u 1.8! Y U NO HERE SOONER?!
				this.wireTileEntity.getWorldObj().setBlockMetadataWithNotify(coordsArray[0], coordsArray[1], coordsArray[2], 15, 3);
			}
		}
	}
	
	/**
	 * Powers the redstone wire.
	 */
	@Override
	public void power(long newPower)
	{
		this.theMeasure.setMeasure("redstone", newPower);
		this.onPower();
	}

	/**
	 * Called when this redstone wire is powered.
	 */
	@Override
	public void onPower() {}

//#########################################################################################
//##########################Methods overriden from vanilla ################################
//#########################################################################################
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }
	
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }
	
	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
    public int getRenderType()
    {
        return 5;
    }
}
