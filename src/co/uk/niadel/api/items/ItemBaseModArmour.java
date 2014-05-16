package co.uk.niadel.api.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import co.uk.niadel.api.annotations.MPIAnnotations.RecommendedMethod;
import co.uk.niadel.api.util.reflection.ReflectionManipulateValues;

/**
 * Yes, Americans, armour is spelled with a U over here. We're English (as in, England English).
 * 
 * 'NGLUND!
 * 
 * END NATIONALITY PRIDE RANT
 * 
 * This is the base for mod armour because of Mojang deciding it'd be a good idea to have an Enum
 * decide the armour type. I made this at night, can you tell?
 * 
 * @author Niadel
 *
 */
public class ItemBaseModArmour extends ItemArmor
{
	private static final String[] CLOTH_OVERLAY_NAMES = {"leather_helmet_overlay", "leather_chestplate_overlay", "leather_leggings_overlay", "leather_boots_overlay"};
	public static ArmorMaterial defaultMaterial = ArmorMaterial.CLOTH;
	public ModArmourMaterial modMaterial;
	public IIcon overlayIcon;
	public IIcon emptySlotIcon;
	
	private ItemBaseModArmour(ArmorMaterial armourMaterial, int renderIndex, int armourType)
	{
		super(armourMaterial, renderIndex, armourType);
	}
	
	@RecommendedMethod
	public ItemBaseModArmour(ModArmourMaterial material, int renderIndex, int armourType)
	{
		super(defaultMaterial, renderIndex, armourType);
		
		try
		{
			this.overlayIcon = (IIcon) ReflectionManipulateValues.getValue(ItemArmor.class, super.instance, "overlayIcon");
		}
		catch (SecurityException | IllegalArgumentException e1)
		{
			e1.printStackTrace();
		}
		
		//Reflection patching, Notch dammit Mojang.
		try
		{
			ReflectionManipulateValues.setValue(ItemArmor.class, super.instance, "material", null);
			ReflectionManipulateValues.setValue(ItemBaseModArmour.class, this, "damageReduceAmount", material.damageReductionArray);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		this.modMaterial = material;
	}
	
	/**
	 * Gets the real armour's mod material.
	 * @return
	 */
	public ModArmourMaterial getArmourMaterial()
	{
		return modMaterial;
	}
	
	//ALL FOLLOWING METHODS ARE OVERRIDED TO USE THE ModArmourMaterial SYSTEM. YOU NEED NOT BOTHER WITH THESE.
	public boolean requiresMultipleRenderPasses()
    {
        return this.modMaterial == ModArmourMaterial.clothArmour;
    }
	
	public int getItemEnchantability()
	{
		return this.modMaterial.enchantability;
	}
	
	public ArmorMaterial getArmorMaterial()
	{
		return null;
	}
	
    public boolean hasColor(ItemStack itemStack)
    {
        return this.modMaterial != ModArmourMaterial.clothArmour ? false : (!itemStack.hasTagCompound() ? false : (!itemStack.getTagCompound().func_150297_b("display", 10) ? false : itemStack.getTagCompound().getCompoundTag("display").func_150297_b("color", 3)));
    }
    
    public int getColor(ItemStack par1ItemStack)
    {
        if (this.modMaterial != ModArmourMaterial.clothArmour)
        {
            return -1;
        }
        else
        {
            NBTTagCompound var2 = par1ItemStack.getTagCompound();

            if (var2 == null)
            {
                return 10511680;
            }
            else
            {
                NBTTagCompound var3 = var2.getCompoundTag("display");
                return var3 == null ? 10511680 : (var3.func_150297_b("color", 3) ? var3.getInteger("color") : 10511680);
            }
        }
    }
    
    public void removeColor(ItemStack par1ItemStack)
    {
        if (this.modMaterial == ModArmourMaterial.clothArmour)
        {
            NBTTagCompound var2 = par1ItemStack.getTagCompound();

            if (var2 != null)
            {
                NBTTagCompound var3 = var2.getCompoundTag("display");

                if (var3.hasKey("color"))
                {
                    var3.removeTag("color");
                }
            }
        }
    }
    
    public void func_82813_b(ItemStack par1ItemStack, int par2)
    {
        if (this.modMaterial != ModArmourMaterial.clothArmour)
        {
            throw new UnsupportedOperationException("Can\'t dye non-leather!");
        }
        else
        {
            NBTTagCompound var3 = par1ItemStack.getTagCompound();

            if (var3 == null)
            {
                var3 = new NBTTagCompound();
                par1ItemStack.setTagCompound(var3);
            }

            NBTTagCompound var4 = var3.getCompoundTag("display");

            if (!var3.func_150297_b("display", 10))
            {
                var3.setTag("display", var4);
            }

            var4.setInteger("color", par2);
        }
    }
    
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return this.modMaterial.getRepairMaterial() == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
    
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);

        if (this.modMaterial == ModArmourMaterial.clothArmour)
        {
            this.overlayIcon  = par1IconRegister.registerIcon(CLOTH_OVERLAY_NAMES[this.armorType]);
        }

        this.emptySlotIcon = par1IconRegister.registerIcon(EMPTY_SLOT_NAMES[this.armorType]);
    }
}
