package co.uk.niadel.mpi.items.interfaces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IDoesInteractForEntity 
{
	public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase);
}
