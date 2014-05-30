package co.uk.niadel.api.events.worldevents;

import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
import co.uk.niadel.api.events.EventBase;
import co.uk.niadel.api.events.IEvent;

/**
 * This can be used to modify what is in the Bonus Chest.
 * @author Niadel
 */
public class EventBonusChestAdded extends EventBase implements IEvent 
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
