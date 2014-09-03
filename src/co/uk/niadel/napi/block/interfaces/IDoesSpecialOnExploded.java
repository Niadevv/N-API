package co.uk.niadel.napi.block.interfaces;

import net.minecraft.world.Explosion;
import net.minecraft.world.World;

/**
 * No, I don't think "exploded" is a word.
 * @author Niadel
 *
 */
public interface IDoesSpecialOnExploded
{
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion);
}
