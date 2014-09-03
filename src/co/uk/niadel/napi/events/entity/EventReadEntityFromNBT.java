package co.uk.niadel.napi.events.entity;

import co.uk.niadel.napi.events.IEvent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Fired when an entity reads data from it's NBT. Can be used to do cool stuff.
 * @author Daniel1
 *
 */
public class EventReadEntityFromNBT implements IEvent
{
	public Entity entity;
	public NBTTagCompound tag;
	
	public EventReadEntityFromNBT(NBTTagCompound tag, Entity entity)
	{
		this.tag = tag;
		this.entity = entity;
	}
}
