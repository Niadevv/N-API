package co.uk.niadel.mpi.common.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Implemented by blocks that can have some form of wrench used on them.
 */
public interface IWrenchable
{
	public void onWrench(ItemStack wrenchItem, EntityPlayer user, World world, Block blockWrenched, int x, int y, int z, int side);
}
