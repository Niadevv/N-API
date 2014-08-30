package co.uk.niadel.mpi.common.entity;

import net.minecraft.entity.Entity;

/**
 * Implemented by entities that are parts of another entity.
 *
 * @author Niadel
 */
public interface IEntityPart
{
	public Entity getParentEntity();

	/**
	 * Whether or not the parent entity should take damage.
	 * @return Whether or not the parent entity should take damage.
	 */
	public boolean passDamageUp();

	/**
	 * Gets how offset this part is from the parent.
	 * @return
	 */
	public double[] getOffsetFromParent();
}
