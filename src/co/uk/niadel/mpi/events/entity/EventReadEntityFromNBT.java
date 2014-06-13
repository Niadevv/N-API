package co.uk.niadel.mpi.events.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Fired when an entity reads data from it's NBT. Can be used to do cool stuff.
 * @author Daniel1
 *
 */
public class EventReadEntityFromNBT
{
	public Entity entity;
	public NBTTagCompound tag;
	
	public EventReadEntityFromNBT(NBTTagCompound tag, Entity entity)
	{
		this.tag = tag;
		this.entity = entity;
	}
}
