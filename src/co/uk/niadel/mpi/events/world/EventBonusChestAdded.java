package co.uk.niadel.mpi.events.world;

import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
import co.uk.niadel.mpi.events.IEvent;

/**
 * This can be used to modify what is in the Bonus Chest.
 * @author Niadel
 */
public class EventBonusChestAdded implements IEvent 
{
	public WorldGeneratorBonusChest chest;
	
	public EventBonusChestAdded(WorldGeneratorBonusChest chest)
	{
		this.chest = chest;
	}

	@Override
	public String getName()
	{
		return "EventBonusChestAdded";
	}
}
