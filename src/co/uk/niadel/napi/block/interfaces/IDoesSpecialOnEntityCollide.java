package co.uk.niadel.napi.block.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface IDoesSpecialOnEntityCollide
{
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity);
}
