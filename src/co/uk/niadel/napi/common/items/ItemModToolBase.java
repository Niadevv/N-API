package co.uk.niadel.napi.common.items;

import java.util.Set;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;

/**
 * Allows for tools from mods. Exists for the same reason as ItemBaseModArmour.
 * @author Daniel1
 *
 */
public class ItemModToolBase extends ItemTool
{
	public ModToolMaterial modToolMaterial;
	public float damageVEntities;
	
	private ItemModToolBase(float toolDamage, ToolMaterial toolMaterial, Set<? extends Block> blocksEffectiveAgainst)
	{
		super(toolDamage, toolMaterial, blocksEffectiveAgainst);
	}
	
	public ItemModToolBase(float toolDamage, ModToolMaterial toolMaterial, Set<? extends Block> blocksEffectiveAgainst)
	{
		this(toolDamage, ToolMaterial.WOOD, blocksEffectiveAgainst);
		this.setMaxDamage(toolMaterial.maxUses);
		this.efficiencyOnProperMaterial = toolMaterial.efficiencyOnProperMaterial;
		this.damageVEntities = toolDamage + toolMaterial.damageVsEntity;
	}
	
	/**
	 * DO NOT USE THIS! IT IS USELESS! USE getToolMaterial() INSTEAD!
	 */
	@Override
	public Item.ToolMaterial func_150913_i()
	{
		return null;
	}
	
	public ModToolMaterial getToolMaterial()
	{
		return this.modToolMaterial;
	}
	
	@Override
	public int getItemEnchantability()
	{
		return this.modToolMaterial.enchantability;
	}
	
	@Override
	public String getToolMaterialName()
	{
		return this.modToolMaterial.toString();
	}
	
	@Override
	public boolean getIsRepairable(ItemStack thisItem, ItemStack repairingItem)
	{
		return this.modToolMaterial.repairItem == repairingItem.getItem();
	}
}
