package co.uk.niadel.mpi.block;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import co.uk.niadel.mpi.entity.tileentity.TileEntityWire;


/**
 * Base for things like pipes from buildcraft or wires from Thermal Expansion.
 * 
 * TODO: Make this not depend on Tile Entities.
 * @author Niadel
 *
 */
public class BlockWireBase extends Block implements ITileEntityProvider
{
	/**
	 * The blocks this block is currently connected to.
	 */
	public Block[] blocksConnectedTo = new Block[6];
	
	/**
	 * This block's Tile Entity, which does the real looking for blocks to connect to.
	 */
	public TileEntityWire wireTileEntity;
	
	/**
	 * Can be used by other blocks to easily get what blocks are surrounding this wire block.
	 */
	public Map<String, Block> blocksSurrounding = new HashMap<>();
	
	public BlockWireBase(Material material)
	{
		super(material);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldObj, int metadata) 
	{
		this.wireTileEntity = new TileEntityWire(worldObj, this);
		return this.wireTileEntity;
	}
	
	/**
	 * Can be overriden to add or reduce the wire types it can connect to.
	 * @param block
	 * @return
	 */
	public boolean canConnectToBlock(Block block)
	{
		if (block instanceof BlockWireBase)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Called when a block is connected to.
	 * @param block
	 * @param direction 
	 */
	public void onConnect(Block block, int direction) {}
}
