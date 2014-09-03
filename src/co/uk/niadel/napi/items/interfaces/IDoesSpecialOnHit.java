package co.uk.niadel.napi.items.interfaces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IDoesSpecialOnHit 
{
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase);
}
