package co.uk.niadel.mpi.events.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import co.uk.niadel.mpi.events.IEvent;

public class EventReadEntityFromNBT implements IEvent
{
	public Entity entity;
	public NBTTagCompound tag;
	
	public EventReadEntityFromNBT(NBTTagCompound tag, Entity entity)
	{
		this.tag = tag;
		this.entity = entity;
	}
	
	@Override
	public String getName()
	{
		return "EventReadEntityFromNBT";
	}

}
