package co.uk.niadel.mpi.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ModToolMaterial
{
	public String materialName;
	public int harvestLevel, efficiencyOnProperMaterial, damageVsEntity, enchantability, maxUses;
	public Item repairItem;
	
	public ModToolMaterial(String name, int harvestLevel, int efficiencyOnProperMaterial, int maxUses, int damageVsEntity, int enchantability)
	{
		this.materialName = name;
		this.harvestLevel = harvestLevel;
		this.efficiencyOnProperMaterial = efficiencyOnProperMaterial;
		this.damageVsEntity = damageVsEntity;
		this.enchantability = enchantability;
		this.maxUses = maxUses;
	}
	
	public ModToolMaterial(String name, int harvestLevel, int efficiencyOnProperMaterial, int maxUses, int damageVsEntity, int enchantability, Block repairMaterial)
	{
		this(name, harvestLevel, efficiencyOnProperMaterial, maxUses, damageVsEntity, enchantability);
		this.repairItem = Item.getItemFromBlock(repairMaterial);
	}
	
	public ModToolMaterial(String name, int harvestLevel, int efficiencyOnProperMaterial, int maxUses, int damageVsEntity, int enchantability, Item repairMaterial)
	{
		this(name, harvestLevel, efficiencyOnProperMaterial, maxUses, damageVsEntity, enchantability);
		this.repairItem = repairMaterial;
	}
}
