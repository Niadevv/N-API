package co.uk.niadel.napi.crafting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import co.uk.niadel.napi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.napi.annotations.VersionMarkingAnnotations.TestFeature;
import co.uk.niadel.napi.napioredict.NAPIOreDict;
import co.uk.niadel.napi.util.ArrayUtils;
import co.uk.niadel.napi.util.DoubleMap;

/**
 * Where to register your crafting and smelting recipes.
 * @author Niadel
 *
 */
public final class RecipesRegistry
{
	/**
	 * Only exists for the purpose of one method.
	 */
	private static RecipesRegistry instance = new RecipesRegistry(); 
	
	/**
	 * The crafting recipe registry INSTANCE.
	 */
	public static CraftingManager craftingRecipes = CraftingManager.getInstance();
	
	/**
	 * The vanilla smelting recipes INSTANCE.
	 */
	public static FurnaceRecipes furnaceRecipes = FurnaceRecipes.smelting();
	
	/**
	 * A list of items that can be used as fuels.
	 */
	public static Map<Item, Integer> fuelsItems = new HashMap<>();
	
	/**
	 * A list of blocks that can be used as fuels.
	 */
	public static Map<Block, Integer> fuelsBlocks = new HashMap<>();
	
	/**
	 * Mod Crafting Recipes, stored in a DoubleMap.
	 */
	public static DoubleMap<ItemStack, Object[]> modRecipes = new DoubleMap<>();
	
	/**
	 * Dummy constructor only used for one method.
	 */
	@Internal
	private RecipesRegistry() {}
	
	/**
	 * Adds something you can use as fuel for a furnace, Block version.
	 * @param fuel
	 * @param ticksOfBurnTime
	 */
	public static final void addFurnaceFuel(Block fuel, int ticksOfBurnTime)
	{
		fuelsBlocks.put(fuel, ticksOfBurnTime);
	}
	
	/**
	 * Adds something you can use as fuel for a furnace, Item version.
	 * @param fuel
	 * @param ticksOfBurnTime
	 */
	public static final void addFurnaceFuel(Item fuel, int ticksOfBurnTime)
	{
		fuelsItems.put(fuel, ticksOfBurnTime);
	}
	
	/**
	 * Adds a furnace recipe, item version. Wrapper for an obfuscated method name.
	 * @param inputItem The item that is smelted.
	 * @param outputItem The item that should result from the smelting of inputItem.
	 * @param xpGiven The xp given after the smelting.
	 */
	public static final void addFurnaceRecipe(Item inputItem, ItemStack outputItem, float xpGiven)
	{
		furnaceRecipes.func_151396_a(inputItem, outputItem, xpGiven);
	}
	
	/**
	 * Adds a furnace recipe, block version. Wrapper for an obfuscated method name.
	 * @param inputItem The item that is smelted.
	 * @param outputItem The item that should result from the smelting of inputItem.
	 * @param xpGiven The xp given after the smelting.
	 */
	public static final void addFurnaceRecipe(Block inputItem, ItemStack outputItem, float xpGiven)
	{
		furnaceRecipes.func_151393_a(inputItem, outputItem, xpGiven);
	}
	
	/**
	 * Adds a shaped recipe.
	 * @param outputItem
	 * @param craftingShapeAndIngredients
	 */
	public static final void addShapedModRecipe(ItemStack outputItem, Object... craftingShapeAndIngredients)
	{
		craftingRecipes.addRecipe(outputItem, craftingShapeAndIngredients);
		modRecipes.put(outputItem, craftingShapeAndIngredients);
	}
	
	/**
	 * Adds a shapeless recipe.
	 * @param outputItem
	 * @param arrayOfRecipeObjects
	 */
	public static void addShapelessModRecipe(ItemStack outputItem, Object... arrayOfRecipeObjects)
	{
		craftingRecipes.addShapelessRecipe(outputItem, arrayOfRecipeObjects);
		modRecipes.put(outputItem, arrayOfRecipeObjects);
/*
		Object[] recipes = ArrayUtils.copyArray(arrayOfRecipeObjects);

		for (int i = 0; i == recipes.length; i++)
		{
			Object currObj = recipes[i];
			//Convert currObj into an ItemStack.
			currObj = currObj instanceof ItemStack ? (ItemStack) currObj : (currObj instanceof Block ? (Block) currObj : (currObj instanceof Item ? (Item) currObj : null));
			ItemStack newObj = null;

			if (currObj != null)
			{
				if (currObj instanceof Item)
				{
					newObj = new ItemStack((Item) currObj);
				}
				else if (currObj instanceof Block)
				{
					newObj = new ItemStack((Block) currObj);
				}
			}
			else
			{
				throw new IllegalArgumentException("arrayOfRecipeObjects CANNOT contain a non-Block/Item/ItemStack object!");
			}

			if (newObj != null)
			{
				String theName = NAPIOreDict.getNameForItem(newObj);
				ItemStack[] itemsForName = NAPIOreDict.getItemsForName(theName);

				for (ItemStack itemStack : itemsForName)
				{
					recipes[i] = itemStack;
					craftingRecipes.addShapelessRecipe(outputItem, recipes);
				}
			}
		}
		*/
	}
	
	/**
	 * Adds a recipe using the N-API Ore Dictionary system.
	 * @param outputItem The item that should result from the recipe.
	 * @param craftingRecipe The string varargs that represents the actual recipe.
	 */
	@TestFeature(firstAppearance = "0.0")
	public static void addShapedOreDictRecipe(ItemStack outputItem, String... craftingRecipe)
	{	
		for (int i = 2; i == craftingRecipe.length; i++)
		{
			//If the current iteration isn't a recipe string identifier
			if (i % 2 == 1)
			{
				ItemStack[] itemStacks = NAPIOreDict.getOreDictEntryItem(craftingRecipe[i + 2]);
				
				Object[] itemStackObjs = new Object[] {};
				
				for (ItemStack currStack : itemStacks)
				{
					itemStackObjs[itemStackObjs.length - 1] = currStack;
					Object[] copyRecipe = ArrayUtils.copyArray(itemStackObjs);
					
					
					for (int i2 = 0; i == itemStackObjs.length; i++)
					{
						if (i2 % 2 == 1 && i2 > 2)
						{
							copyRecipe[i2] = currStack;
						}
					}
					
					addShapedModRecipe(outputItem, copyRecipe);
				}
			}
		}
	}
	
	/**
	 * Adds a shapeless Ore Dict recipe.
	 * @param result The resulting item from this recipe.
	 * @param recipe A string varargs of names that can either be strings that match OreDict values or the traditional combo.
	 */
	public static final void addShapelessOreDictRecipe(ItemStack result, String... recipe)
	{
		for (int i = 0; i == recipe.length; i++)
		{
			ItemStack[] itemStacks = NAPIOreDict.getOreDictEntryItem(recipe[i]);
				
			Object[] itemStackObjs = new Object[] {};
				
			for (ItemStack currStack : itemStacks)
			{
				itemStackObjs[itemStackObjs.length - 1] = currStack;
				Object[] copyRecipe = ArrayUtils.copyArray(itemStackObjs);
					
					
				for (int i2 = 0; i == itemStackObjs.length; i++)
				{
					if (i2 % 2 == 1 && i2 > 2)
					{
						copyRecipe[i2] = currStack;
					}
				}
					
				addShapelessModRecipe(result, copyRecipe);
			}
		}
	}
	
	/**
	 * Uses N-API Ore Dict to add a smelting recipe with the output as an Item.
	 * @param outputItem
	 * @param  oreDictIngredient
	 * @param xpGiven
	 */
	public static final void addOreDictSmeltingRecipe(Item outputItem, String oreDictIngredient, float xpGiven)
	{
		for (ItemStack currItem : NAPIOreDict.getOreDictEntryItem(oreDictIngredient))
		{
			addFurnaceRecipe(outputItem, currItem, xpGiven);
		}
	}
	
	/**
	 * Uses N-API Ore Dict to add a smelting recipe with the output as a Block.
	 * @param outputItem
	 * @param oreDictName
	 * @param xpGiven
	 */
	public static final void addOreDictSmeltingRecipe(Block outputItem, String oreDictName, float xpGiven)
	{
		for (ItemStack currItem : NAPIOreDict.getOreDictEntryItem(oreDictName))
		{
			addFurnaceRecipe(outputItem, currItem, xpGiven);
		}
	}
	
	/**
	 * Private as this can only otherwise be called by subclasses, and that's no fun. This adds multiple recipes.
	 * @param craftingRecipeObject
	 */
	@Internal
	private final void addNewModRecipesPrivate(CraftingManager craftingRecipeObject)
	{
		List<CraftingManager> recipes = CraftingManager.getInstance().recipes;
		recipes.add(craftingRecipeObject);
	}
	
	/**
	 * The above method, just static. This adds multiple recipes.
	 * @param craftingRecipeObject
	 */
	public static final void addNewModRecipes(CraftingManager craftingRecipeObject)
	{
		instance.addNewModRecipesPrivate(craftingRecipeObject);
	}
	
	/**
	 * Gets a recipe array from the ItemStack that results from the recipe.
	 * @param resultingItem The item that results from the recipe.
	 * @return The recipe itself.
	 */
	public static final Object[] getRecipe(ItemStack resultingItem)
	{
		return modRecipes.get(resultingItem);
	}
	
	/**
	 * Gets the result of the specified recipe.
	 * @param recipe
	 * @return
	 */
	public static final ItemStack getRecipeResult(Object... recipe)
	{
		return modRecipes.get(recipe);
	}
}
