package co.uk.niadel.napi.events.world;

import co.uk.niadel.napi.events.IEvent;
import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;

/**
 * This can be used to modify what is in the Bonus Chest.
 * @author Niadel
 */
public class EventBonusChestAdded implements IEvent
{
	/**
	 * The chest that is generated.
	 */
	public WorldGeneratorBonusChest chest;
	
	public EventBonusChestAdded(WorldGeneratorBonusChest chest)
	{
		this.chest = chest;
	}
}
