package co.uk.niadel.napi.block.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface IDoesSpecialWhenFallenOn
{
	public void onFallenUpon(World world, int x, int y, int z, Entity entity, float p_149746_6_);
}
