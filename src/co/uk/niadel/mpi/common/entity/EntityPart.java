package co.uk.niadel.mpi.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * A part of a larger entity. Can pass damage up to it's parent entity.
 *
 * @author Niadel
 */
public class EntityPart extends Entity implements IEntityPart
{
	/**
	 * The entity this part is a part of.
	 */
	public Entity parent;

	public EntityPart(Entity parent)
	{
		this(parent.worldObj);
		this.parent = parent;
	}

	/**
	 * Just a necessary override. You shouldn't use this, you should use the above constructor.
	 * @param world
	 */
	private EntityPart(World world)
	{
		super(world);
	}

	@Override
	public boolean hitByEntity(Entity attacker)
	{
		if (this.passDamageUp())
		{
			return this.parent.hitByEntity(attacker);
		}
		else
		{
			return false;
		}
	}

	@Override
	public Entity getParentEntity()
	{
		return this.parent;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setInteger("ParentID", this.parent.getEntityId());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		this.parent = this.worldObj.getEntityByID(tag.getInteger("ParentID"));
	}

	/**
	 * Override and return false if you want this part to take damage independent of the parent.
	 * @return True if you want this part to take damage independent of the parent.
	 */
	@Override
	public boolean passDamageUp()
	{
		return true;
	}

	@Override
	public void entityInit()
	{
		//Scout: Uh, NO!
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float damage)
	{
		if (this.passDamageUp())
		{
			return this.parent.attackEntityFrom(damageSource, damage);
		}
		else
		{
			return this.attackEntityFrom(damageSource, damage);
		}
	}

	@Override
	public void onStruckByLightning(EntityLightningBolt lightningBolt)
	{
		if (this.passDamageUp())
		{
			this.parent.onStruckByLightning(lightningBolt);
		}
		else
		{
			this.onStruckByLightning(lightningBolt);
		}
	}

	@Override
	public void onKillEntity(EntityLivingBase entity)
	{
		this.parent.onKillEntity(entity);
	}

	@Override
	public boolean canAttackWithItem()
	{
		return this.parent.canAttackWithItem();
	}

	@Override
	public double[] getOffsetFromParent()
	{
		return new double[] {0.0D, 0.0D, 0.0D};
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		double[] offset = this.getOffsetFromParent();
		this.posX = this.parent.posX + offset[0];
		this.posY = this.parent.posY + offset[1];
		this.posZ = this.parent.posZ + offset[2];
	}
}
