package co.uk.niadel.mpi.events.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Fired when an entity is written to NBT.
 * @author Niadel
 *
 */
public class EventWriteEntityToNBT
{
	public NBTTagCompound tag;
	public Entity entity;
	
	public EventWriteEntityToNBT(NBTTagCompound tag, Entity entity)
	{
		this.tag = tag;
		this.entity = entity;
	}
}
