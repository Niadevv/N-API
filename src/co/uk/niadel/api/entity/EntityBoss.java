package co.uk.niadel.api.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.world.World;

/**
 * Base for boss mobs.
 * 
 * @author Niadel
 */
public class EntityBoss extends EntityMob implements IBossDisplayData
{
	Entity projectileEntity;
	Entity targetedEntity;
	
	public EntityBoss(World world)
	{
		super(world);
	}
	
	/**
	 * A utility method that attacks a mob with a projectile. Is customisable, just pass a target, a projectile Object, like
	 * a wither skull or an arrow, and the offset from the entity.
	 */
	public final void attackEnemyFromRange(Entity target, Entity projectile, double xOffset, double yOffset, double zOffset)
	{
		this.targetedEntity = target;
		Entity projectileEntity = projectile;
		
		double projectilePosX = this.posX;
		double projectilePosY = this.posY;
		double projectilePosZ = this.posZ;
		double projectileMotionX = this.targetedEntity.posX - this.posX;
		double projectileMotionY = (this.targetedEntity.boundingBox.maxY - this.targetedEntity.boundingBox.minY) + (double)(this.targetedEntity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
		double projectileMotionZ = this.targetedEntity.posZ - this.posZ;
		this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(projectileMotionX, projectileMotionZ)) * 180.0F / (float)Math.PI;
		
		projectileEntity.motionX = projectileMotionX;
		projectileEntity.motionY = projectileMotionY;
		projectileEntity.motionZ = projectileMotionZ;
		
		this.worldObj.spawnEntityInWorld(projectileEntity);
	}
	
	/**
	 * Sets the projectile the entity should use.
	 */
	public final void setProjectile(Entity theProjectile)
	{
		this.projectileEntity = theProjectile;
	}
	
	@Override
	public final void setTarget(Entity target)
	{
		this.targetedEntity = target;
	}
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
	}
}
