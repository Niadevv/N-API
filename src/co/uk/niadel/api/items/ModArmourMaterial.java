package co.uk.niadel.api.items;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * Used to substitute the vanilla enums system.
 * 
 * @author Niadel
 *
 */
public class ModArmourMaterial
{
	public final int maxDamageFactor;
	public final int[] damageReductionArray;
	public final int enchantability;
	public final String materialName;
	
	public static ModArmourMaterial clothArmour = new ModArmourMaterial("CLOTH", 5, new int[]{1, 3, 2, 1}, 15);
	public static ModArmourMaterial chainArmour = new ModArmourMaterial("CHAIN", 15, new int[]{2, 5, 4, 1}, 12);
	public static ModArmourMaterial ironArmour = new ModArmourMaterial("IRON", 15, new int[]{2, 6, 5, 2}, 9);
	public static ModArmourMaterial goldArmour = new ModArmourMaterial("GOLD", 7, new int[]{2, 5, 3, 1}, 25);
	public static ModArmourMaterial diamondArmour = new ModArmourMaterial("DIAMOND", 33, new int[]{3, 8, 6, 3}, 10);
	
	public ModArmourMaterial(String materialName, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability)
	{
		this.maxDamageFactor = maxDamageFactor;
		this.enchantability = enchantability;
		this.damageReductionArray = damageReductionAmountArray;
		this.materialName = materialName;
	}
	
	public Item getRepairMaterial()
    {
        return this == clothArmour ? Items.leather : (this == chainArmour ? Items.iron_ingot : (this == goldArmour ? Items.gold_ingot : (this == ironArmour ? Items.iron_ingot : (this == diamondArmour ? Items.diamond : null))));
    }
}
