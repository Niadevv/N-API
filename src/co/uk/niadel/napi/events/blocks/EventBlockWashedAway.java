package co.uk.niadel.napi.events.blocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;

/**
 * Called when a block is washed away/destroyed by water. Added per request of ExclamationMark on the Minecraft Forums.
 *
 * @author Niadel
 */
public class EventBlockWashedAway
{
	/**
	 * The coords of the block washed away.
	 */
	public int x, y, z;

	/**
	 * The world object.
	 */
	public World world;

	/**
	 * The Block washed away.
	 */
	public Block blockWashedAway;

	public EventBlockWashedAway(World world, int x, int y, int z, Block blockWashedAway)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = world;
		this.blockWashedAway = blockWashedAway;
	}
}
