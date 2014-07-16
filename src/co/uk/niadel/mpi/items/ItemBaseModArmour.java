package co.uk.niadel.mpi.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import co.uk.niadel.mpi.annotations.MPIAnnotations.RecommendedMethod;
import co.uk.niadel.mpi.util.reflection.ReflectionManipulateValues;

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
public abstract class ItemBaseModArmour extends ItemArmor
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
		this.overlayIcon = (IIcon) ReflectionManipulateValues.getValue(ItemArmor.class, /* Remember that instance thing in Item? This is what that was for. */super.instance, "overlayIcon");
		
		ReflectionManipulateValues.setValue(ItemArmor.class, super.instance, "material", null);
		ReflectionManipulateValues.setValue(ItemBaseModArmour.class, this, "damageReduceAmount", material.damageReductionArray);
		
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
	
//###########################################################################################################	
//ALL FOLLOWING METHODS ARE OVERRIDEN TO USE THE ModArmourMaterial SYSTEM. YOU NEED NOT BOTHER WITH THESE.
//###########################################################################################################
	
	@Override
	public boolean requiresMultipleRenderPasses()
    {
        return this.modMaterial == ModArmourMaterial.clothArmour;
    }
	
	@Override
	public int getItemEnchantability()
	{
		return this.modMaterial.enchantability;
	}
	
	@Override
	public ArmorMaterial getArmorMaterial()
	{
		return null;
	}
	
	@Override
    public boolean hasColor(ItemStack itemStack)
    {
        return this.modMaterial != ModArmourMaterial.clothArmour ? false : (!itemStack.hasTagCompound() ? false : (!itemStack.getTagCompound().func_150297_b("display", 10) ? false : itemStack.getTagCompound().getCompoundTag("display").func_150297_b("color", 3)));
    }
    
	@Override
    public int getColor(ItemStack par1ItemStack)
    {
        if (this.modMaterial != ModArmourMaterial.clothArmour)
        {
            return -1;
        }
        else
        {
            NBTTagCompound itemStackCompoundTag = par1ItemStack.getTagCompound();

            if (itemStackCompoundTag == null)
            {
                return 10511680;
            }
            else
            {
                NBTTagCompound displayTag = itemStackCompoundTag.getCompoundTag("display");
                return displayTag == null ? 10511680 : (displayTag.func_150297_b("color", 3) ? displayTag.getInteger("color") : 10511680);
            }
        }
    }
    
	@Override
    public void removeColor(ItemStack itemStack)
    {
        if (this.modMaterial == ModArmourMaterial.clothArmour)
        {
            NBTTagCompound itemStackCompoundTag = itemStack.getTagCompound();

            if (itemStackCompoundTag != null)
            {
                NBTTagCompound displayTag = itemStackCompoundTag.getCompoundTag("display");

                if (displayTag.hasKey("color"))
                {
                    displayTag.removeTag("color");
                }
            }
        }
    }
    
	@Override
    public void func_82813_b(ItemStack par1ItemStack, int colourNumber)
    {
        if (this.modMaterial != ModArmourMaterial.clothArmour)
        {
            throw new UnsupportedOperationException("Can\'t dye non-leather!");
        }
        else
        {
            NBTTagCompound itemStackCompoundTag = par1ItemStack.getTagCompound();

            if (itemStackCompoundTag == null)
            {
            	itemStackCompoundTag = new NBTTagCompound();
                par1ItemStack.setTagCompound(itemStackCompoundTag);
            }

            NBTTagCompound displayTag = itemStackCompoundTag.getCompoundTag("display");

            if (!itemStackCompoundTag.func_150297_b("display", 10))
            {
            	itemStackCompoundTag.setTag("display", displayTag);
            }

            displayTag.setInteger("color", colourNumber);
        }
    }
    
	@Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
        return this.modMaterial.getRepairMaterial() == par2ItemStack.getItem() ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }
    
	@Override
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
