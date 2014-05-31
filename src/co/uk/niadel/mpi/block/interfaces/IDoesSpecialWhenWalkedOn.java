package co.uk.niadel.mpi.block.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface IDoesSpecialWhenWalkedOn
{
	public void onEntityWalking(World world, int x, int y, int z, Entity entity);
}
