package co.uk.niadel.mpi.events.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import co.uk.niadel.mpi.events.IEvent;

/**
 * Fired when an entity is written to NBT.
 * @author Niadel
 *
 */
public class EventWriteEntityToNBT implements IEvent
{
	public NBTTagCompound tag;
	public Entity entity;
	
	public EventWriteEntityToNBT(NBTTagCompound tag, Entity entity)
	{
		this.tag = tag;
		this.entity = entity;
	}
	
	@Override
	public String getName()
	{
		return "EventWriteEntityToNBT";
	}

}
