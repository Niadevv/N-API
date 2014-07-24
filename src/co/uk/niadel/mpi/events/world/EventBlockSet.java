package co.uk.niadel.mpi.events.world;

import co.uk.niadel.mpi.events.IEvent;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class EventBlockSet implements IEvent
{
	public int x, y, z, metadata, flags;
	public Block blockSet;
	public World world;
	
	public EventBlockSet(int x, int y, int z, Block blockSet, int metadata, int flags, World world)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockSet = blockSet;
		this.metadata = metadata;
		this.flags = flags;
		this.world = world;
	}
}
