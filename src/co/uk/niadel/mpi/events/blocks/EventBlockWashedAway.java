package co.uk.niadel.mpi.events.blocks;

import co.uk.niadel.mpi.annotations.VersionMarkingAnnotations.NYI;
import net.minecraft.block.Block;
import net.minecraft.world.World;

/**
 * Called when a block is washed away/destroyed by water. Added per request of ExclamationMark on the Minecraft Forums.
 *
 * TEMP: Is BlockDynamicLiquid.func_149813_h the method that washes away block?
 *
 * @author Niadel
 */
@NYI(firstPresence = "0.0")
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
