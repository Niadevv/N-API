package co.uk.niadel.mpi.block.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IDoesSpecialOnHarvest
{
	public void onBlockHarvested(World world, int x, int y, int z, int p_149681_5_, EntityPlayer player);
}
