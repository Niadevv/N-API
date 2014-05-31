package co.uk.niadel.api.block;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import co.uk.niadel.api.entity.tileentity.TileEntityWire;


/**
 * Base for things like pipes from buildcraft or wires from Thermal Expansion
 * @author Niadel
 *
 */
public abstract class BlockWireBase extends Block implements ITileEntityProvider
{
	public Block[] blocksConnectedTo = new Block[6];
	public TileEntityWire wireTileEntity;
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
	 * Can be overriden to add more wires it can connect to.
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
	 */
	public abstract void onConnect(Block block);
}
