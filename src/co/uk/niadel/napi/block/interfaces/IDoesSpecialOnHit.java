package co.uk.niadel.napi.block.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * The method is called when it's just left clicked.
 * @author Niadel
 *
 */
public interface IDoesSpecialOnHit
{
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player);
}
