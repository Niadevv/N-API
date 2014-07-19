package co.uk.niadel.mpi.common.entity;

import java.lang.reflect.InvocationTargetException;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.world.World;

/**
 * Base for boss mobs, giving a projectile method for convenience. Due to inheritance, all that extend this will not
 * need to implement IBossDisplayData.
 * 
 * @author Niadel
 */
public class EntityBoss extends EntityMob implements IBossDisplayData
{
	/**
	 * The entity that is being launched. Can be anything. By default, it is an arrow.
	 */
	protected Entity projectileEntity;
	
	public EntityBoss(World world)
	{
		super(world);
		this.projectileEntity = new EntityArrow(this.worldObj);
	}
	
	/**
	 * A utility method that attacks a mob with a projectile. Is customisable, just pass a target and the offset from the entity.
	 * Don't use too much as it does use reflection.
	 */
	public void attackEnemyFromRange(Entity target, double xOffset, double yOffset, double zOffset)
	{
		try
		{
			Entity targetedEntity = target;
			Entity projectileEntity = this.projectileEntity.getClass().getConstructor(new Class[] {World.class}).newInstance(this.projectileEntity.worldObj);

			double projectilePosX = this.posX + xOffset;
			double projectilePosY = this.posY + yOffset;
			double projectilePosZ = this.posZ + zOffset;
			double projectileMotionX = targetedEntity.posX - this.posX;
			double projectileMotionY = (targetedEntity.boundingBox.maxY - targetedEntity.boundingBox.minY) + (double)(targetedEntity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
			double projectileMotionZ = targetedEntity.posZ - this.posZ;
			this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(projectileMotionX, projectileMotionZ)) * 180.0F / (float)Math.PI;

			projectileEntity.motionX = projectileMotionX;
			projectileEntity.motionY = projectileMotionY;
			projectileEntity.motionZ = projectileMotionZ;

			this.worldObj.spawnEntityInWorld(projectileEntity);
		}
		catch (InvocationTargetException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
	}
	
	public void attackEntityFromRange(Entity target, Entity projectile, double xOffset, double yOffset, double zOffset)
	{
		double projectilePosX = this.posX + xOffset;
		double projectilePosY = this.posY + yOffset;
		double projectilePosZ = this.posZ + zOffset;
		double projectileMotionX = target.posX - this.posX;
		double projectileMotionY = (target.boundingBox.maxY - target.boundingBox.minY) + (double)(target.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
		double projectileMotionZ = target.posZ - this.posZ;
		this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(projectileMotionX, projectileMotionZ)) * 180.0F / (float)Math.PI;

		projectile.motionX = projectileMotionX;
		projectile.motionY = projectileMotionY;
		projectile.motionZ = projectileMotionZ;

		this.worldObj.spawnEntityInWorld(projectileEntity);

	}
	
	/**
	 * Sets the projectile the entity should use in the first attackEntityFromRange.
	 */
	public void setProjectile(Entity theProjectile)
	{
		this.projectileEntity = theProjectile;
	}
}
