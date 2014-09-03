package co.uk.niadel.napi.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.*;

/**
 * Base for mod swords, exists for same reason as ItemModToolBase.
 * @author Niadel
 *
 */
public class ItemModSwordBase extends ItemSword
{
	public ModToolMaterial toolMaterial;
	public float damageDealt;
	
	public ItemModSwordBase(ToolMaterial toolMaterial)
	{
		super(null);
	}
	
	public ItemModSwordBase(int damage, ModToolMaterial toolMaterial)
	{
		this(ToolMaterial.WOOD);
		this.toolMaterial = toolMaterial;
		this.setMaxDamage(toolMaterial.maxUses);
		this.damageDealt = damage + toolMaterial.damageVsEntity;
	}
	
	@Override
	public float func_150931_i()
	{
		return this.toolMaterial.damageVsEntity;
	}
	
	public float getDamageVsEntity()
	{
		return this.func_150931_i();
	}
	
	public float getItemEffectivenessVBlock(ItemStack thisItem, Block blockMined)
	{
		return this.func_150893_a(thisItem, blockMined);
	}
	
	@Override
	public String func_150932_j()
	{
		return this.toolMaterial.toString();
	}
	
	public String getSwordMaterialName()
	{
		return this.func_150932_j();
	}
	
	@Override
	public boolean getIsRepairable(ItemStack thisItem, ItemStack repairingItem)
	{
		return this.toolMaterial.repairItem == repairingItem.getItem();
	}
}
