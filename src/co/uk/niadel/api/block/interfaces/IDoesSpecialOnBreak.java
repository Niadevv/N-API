package co.uk.niadel.api.block.interfaces;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public interface IDoesSpecialOnBreak
{
	public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_);
}
