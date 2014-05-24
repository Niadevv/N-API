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
	/**
	 * The entity that is being launched. Maybe it could be a Creeper XD.
	 */
	protected Entity projectileEntity;
		
	public EntityBoss(World world)
	{
		super(world);
	}
	
	/**
	 * A utility method that attacks a mob with a projectile. Is customisable, just pass a target, a projectile Object, like
	 * a wither skull or an arrow, and the offset from the entity.
	 */
	public void attackEnemyFromRange(Entity target, double xOffset, double yOffset, double zOffset)
	{
		Entity targetedEntity = target;
		Entity projectileEntity = this.projectileEntity;
		
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
	
	/**
	 * Sets the projectile the entity should use.
	 */
	public void setProjectile(Entity theProjectile)
	{
		this.projectileEntity = theProjectile;
	}
	
	/**
	 * Override of onLivingUpdate.
	 */
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
	}
}
