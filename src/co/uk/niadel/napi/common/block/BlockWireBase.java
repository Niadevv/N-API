package co.uk.niadel.napi.common.block;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import co.uk.niadel.napi.entity.tileentity.TileEntityWire;
import co.uk.niadel.napi.measuresapi.ModMeasureBase;
import co.uk.niadel.napi.util.BlockDirectionsMod;
import co.uk.niadel.napi.util.MCUtils;


/**
 * Base for things like pipes from buildcraft or wires from Thermal Expansion.
 * 
 * TODO: Make this not depend on Tile Entities.
 * @author Niadel
 *
 */
public class BlockWireBase extends Block implements ITileEntityProvider, IMeasureTransferer, BlockDirectionsMod
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
	 * This wire's measure that is transferred between containers and wires.
	 */
	public ModMeasureBase theMeasure;
	
	/**
	 * Can be used by other blocks to easily get what blocks are surrounding this wire block. TODO replace the String param
	 * with EnumFacing in 1.8.
	 */
	public Map<String, Block> blocksSurrounding = new HashMap<>();
	
	public BlockWireBase(Material material, ModMeasureBase measure)
	{
		super(material);
		this.theMeasure = measure;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldObj, int metadata) 
	{
		this.wireTileEntity = new TileEntityWire(worldObj, this);
		return this.wireTileEntity;
	}
	
	/**
	 * Can be overridden to add or reduce the wire types it can connect to.
	 * @param block
	 * @param direction The direction this block is charged from. TODO Change to use EnumFacing in 1.8.
	 * @return
	 */
	public boolean canTransferToBlock(Block block, int direction)
	{
		return block instanceof IMeasureTransferer;
	}
	
	/**
	 * Called when a block is connected to.
	 * @param block
	 * @param direction
	 * @return Whether or not this block connected successfully.
	 */
	public void onConnect(Block block, int direction) {}

	@Override
	public void transferMeasure(IMeasureTransferer target, ModMeasureBase measure)
	{
		measure.incrementMeasure(1);
		this.theMeasure.decrementMeasure(1);
	}

	@Override
	public void onReceiveMeasure(IMeasureTransferer transferer, ModMeasureBase measure)
	{
		this.theMeasure.incrementMeasure(1);
		measure.decrementMeasure(1);
	}

	@Override
	public boolean canTransferTo(IMeasureTransferer target)
	{
		return true;
	}

	@Override
	public ModMeasureBase getMeasure()
	{
		return this.theMeasure;
	}
	
	public int[] getCoordsFromDirection(int direction)
	{
		int[] theCoords = MCUtils.getCoordsOfTE(this.wireTileEntity);
		
		switch (direction)
		{
			case UP:
				theCoords[1] += 1;
				break;
			
			case DOWN:
				theCoords[1] -= 1;
				break;
			
			case LEFT:
				theCoords[0] += 1;
				break;
			
			case RIGHT:
				theCoords[0] -= 1;
				break;
				
			case FORWARDS:
				theCoords[2] += 1;
				break;
			
			case BACKWARDS:
				theCoords[2] -= 1;
				break;
			
			case DIAG_LEFT_UP:
				theCoords[1] += 1;
				theCoords[0] += 1;
				break;
			
			case DIAG_LEFT_DOWN:
				theCoords[1] -= 1;
				theCoords[0] += 1;
				break;
			
			case DIAG_RIGHT_UP:
				theCoords[1] += 1;
				theCoords[0] -= 1;
				break;
			
			case DIAG_RIGHT_DOWN:
				theCoords[1] -= 1;
				theCoords[0] -= 1;
				break;
				
			case DIAG_FORWARDS_UP:
				theCoords[1] += 1;
				theCoords[2] += 1;
				break;
				
			case DIAG_FORWARDS_DOWN:
				theCoords[1] -= 1;
				theCoords[2] += 1;
				break;
				
			case DIAG_BACKWARDS_UP:
				theCoords[1] += 1;
				theCoords[2] -= 1;
				break;
				
			case DIAG_BACKWARDS_DOWN:
				theCoords[1] -= 1;
				theCoords[2] -= 1;
				break;
			
			default:
				throw new IllegalArgumentException("The specified direction " + direction + " is not a correct direction! It must be in the range of 0-5!");
		}
		
		return theCoords;
	}
}
