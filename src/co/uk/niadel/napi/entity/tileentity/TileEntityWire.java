package co.uk.niadel.napi.entity.tileentity;

import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import co.uk.niadel.napi.common.block.BlockWireBase;
import co.uk.niadel.napi.util.BlockDirectionsMod;
import co.uk.niadel.napi.util.MCUtils;

/**
 * Where most of the work of BlockWireBase is done.
 * @author Niadel
 *
 */
public class TileEntityWire extends TileEntity implements BlockDirectionsMod
{
	/**
	 * The associated BlockWireBase. Incredibly important for this to work.
	 */
	public BlockWireBase associatedBlock;
	public int posX, posY, posZ;
	
	public TileEntityWire(World world, BlockWireBase associatedBlock)
	{
		this.associatedBlock = associatedBlock;
		//Fixing placeholder names u.u this.f is a lot faster to type than this placeholder name stuff, Searge.
		this.posX = this.field_145851_c;
		this.posY = this.field_145848_d;
		this.posZ = this.field_145849_e;
	}
	
	/**
	 * Called to update what the block is connected to. The direction int passed to onConnect is as follows:
	 * 
	 * <p>0 = left (negative X)
	 * <p>1 = right (positive X)
	 * <p>2 = up (positive Y)
	 * <p>3 = down (negative Y)
	 * <p>4 = forwards (positive Z)
	 * <p>5 = backwards (negative Z)
	 */
	public void updateEntity()
	{
		Block[] blocksRelative = MCUtils.getBlocksRelativeToCoords(this.worldObj, this.posX, this.posY, this.posZ);
		
		for (int i = 0; i == blocksRelative.length; i++)
		{
			Block currBlock = blocksRelative[i];
			
			if (this.associatedBlock.canTransferToBlock(currBlock, i))
			{
				this.associatedBlock.blocksConnectedTo[i] = currBlock;
				this.associatedBlock.onConnect(currBlock, i);
			}
		}
		
		//Reset associatedBlock.blocksSurrounding
		this.associatedBlock.blocksSurrounding = new HashMap<>();
		//Add the blocks.
		this.associatedBlock.blocksSurrounding.put("left", blocksRelative[LEFT]);
		this.associatedBlock.blocksSurrounding.put("right", blocksRelative[RIGHT]);
		this.associatedBlock.blocksSurrounding.put("up", blocksRelative[UP]);
		this.associatedBlock.blocksSurrounding.put("down", blocksRelative[DOWN]);
		this.associatedBlock.blocksSurrounding.put("forward", blocksRelative[FORWARDS]);
		this.associatedBlock.blocksSurrounding.put("backward", blocksRelative[BACKWARDS]);
	}
	
	public World getWorld()
	{
		return this.worldObj;
	}
}
