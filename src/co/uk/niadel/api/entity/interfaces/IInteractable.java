package co.uk.niadel.api.entity.interfaces;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Provides the method to allow one to have entity interaction.
 * @author Niadel
 *
 */
public interface IInteractable 
{
	public boolean interactFirst(EntityPlayer player);
}
