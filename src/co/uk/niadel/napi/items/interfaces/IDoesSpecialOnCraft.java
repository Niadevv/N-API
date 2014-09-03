package co.uk.niadel.napi.items.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IDoesSpecialOnCraft 
{
	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer);
}
