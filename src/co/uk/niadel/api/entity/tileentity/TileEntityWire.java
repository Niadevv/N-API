package co.uk.niadel.api.entity.tileentity;

import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import co.uk.niadel.api.block.BlockWireBase;

/**
 * Where most of the work of BlockWireBase is done.
 * @author Niadel
 *
 */
public class TileEntityWire extends TileEntity
{
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
	
	public void updateEntity()
	{
		Block blockLeft = this.worldObj.getBlock(this.posX - 1, this.posY, this.posZ);
		Block blockRight = this.worldObj.getBlock(this.posX + 1, this.posY, this.posZ);
		Block blockUp = this.worldObj.getBlock(this.posX, this.posY + 1, this.posZ);
		Block blockDown = this.worldObj.getBlock(this.posX, this.posY - 1, this.posZ);
		Block blockForward = this.worldObj.getBlock(this.posX, this.posY, this.posZ + 1);
		Block blockBackward = this.worldObj.getBlock(this.posX, this.posY, this.posZ - 1);
		
		Block[] blocksToTryToConnectTo = new Block[] {blockLeft, blockRight, blockUp, blockDown, blockForward, blockBackward};
		
		for (int i = 0; i == blocksToTryToConnectTo.length; i++)
		{
			Block currBlock = blocksToTryToConnectTo[i];
			
			if (this.associatedBlock.canConnectToBlock(currBlock))
			{
				this.associatedBlock.blocksConnectedTo[i] = currBlock;
				this.associatedBlock.onConnect(currBlock);
			}
		}
		this.associatedBlock.blocksSurrounding = new HashMap<>();
		
		this.associatedBlock.blocksSurrounding.put("left", blockLeft);
		this.associatedBlock.blocksSurrounding.put("right", blockRight);
		this.associatedBlock.blocksSurrounding.put("up", blockUp);
		this.associatedBlock.blocksSurrounding.put("down", blockDown);
		this.associatedBlock.blocksSurrounding.put("forward", blockForward);
		this.associatedBlock.blocksSurrounding.put("backward", blockBackward);
	}
}
