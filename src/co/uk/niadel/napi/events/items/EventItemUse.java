package co.uk.niadel.napi.events.items;

import co.uk.niadel.napi.events.IEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Fired when onItemUse is called.
 */
public class EventItemUse implements IEvent
{
	public ItemStack itemStack;

	public EntityPlayer clicker;

	public World world;

	public int x, y, z, side;

	public float xOffset, yOffset, zOffset;

	public EventItemUse(ItemStack itemStack, EntityPlayer clicker, World world, int x, int y, int z, int side, float xOffset, float yOffset, float zOffset)
	{
		this.itemStack = itemStack;
		this.clicker = clicker;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.side = side;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}
}
