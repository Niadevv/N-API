package co.uk.niadel.napi.common.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Implemented by blocks that can have some form of wrench used on them.
 */
public interface IWrenchable
{
	/**
	 * Called when this block is wrenched.
	 * @param wrenchItem The item that was used to wrench this block
	 * @param user The user who wrenched this block.
	 * @param world The world object.
	 * @param blockWrenched The block that has been wrenched.
	 * @param x The block's x coord.
	 * @param y The block's y coord.
	 * @param z The block's z coord.
	 * @param side The side wrenched. TODO replace this with an EnumFacing in 1.8
	 */
	public void onWrench(ItemStack wrenchItem, EntityPlayer user, World world, Block blockWrenched, int x, int y, int z, int side);
}
