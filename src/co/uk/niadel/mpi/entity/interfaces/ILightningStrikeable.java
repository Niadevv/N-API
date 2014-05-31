package co.uk.niadel.mpi.entity.interfaces;

import net.minecraft.entity.effect.EntityLightningBolt;

/**
 * Provides the method for having it do something when hit by lightning.
 * @author Niadel
 *
 */
public interface ILightningStrikeable 
{
	public void onStruckByLightning(EntityLightningBolt lightning);
}
