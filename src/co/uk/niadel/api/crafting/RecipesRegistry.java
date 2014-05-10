package co.uk.niadel.api.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import co.uk.niadel.api.util.reflection.ReflectionManipulateValues;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ReportedException;

/**
 * Where to register your crafting recipes.
 * @author Niadel
 *
 */
public final class RecipesRegistry extends CraftingManager
{
	static CraftingManager modRecipeList = CraftingManager.getInstance();
	static FurnaceRecipes recipes = FurnaceRecipes.smelting();
	
	/**
	 * Adds a furnace recipe, item version.
	 * @param inputItem
	 * @param outputItem
	 * @param xpGiven
	 */
	public static final void addFurnaceRecipe(Item inputItem, ItemStack outputItem, float xpGiven)
	{
		recipes.func_151396_a(inputItem, outputItem, xpGiven);
	}
	
	/**
	 * Adds a furnace recipe, block version.
	 * @param inputItem
	 * @param outputItem
	 * @param xpGiven
	 */
	public static final void addFurnaceRecipe(Block inputItem, ItemStack outputItem, float xpGiven)
	{
		recipes.func_151393_a(inputItem, outputItem, xpGiven);
	}
	
	/**
	 * Adds a shaped recipe.
	 * @param outputItem
	 * @param craftingShapeAndIngredients
	 */
	public static final void addShapedModRecipe(ItemStack outputItem, Object[] craftingShapeAndIngredients)
	{
		modRecipeList.addRecipe(outputItem, craftingShapeAndIngredients);
	}
	
	/**
	 * Adds a shapeless recipe.
	 * @param outputItem
	 * @param arrayOfRecipeObjects
	 */
	protected static void addModShapelessRecipe(ItemStack outputItem, Object[] arrayOfRecipeObjects)
	{
		modRecipeList.addShapelessRecipe(outputItem, arrayOfRecipeObjects);
	}
	
	/**
	 * Unfortunately, can only be called in subclasses. Will be fixed at a later date.
	 * @param craftingRecipeObject
	 */
	public final void addNewModRecipes(CraftingManager craftingRecipeObject)
	{
		//Mojang! Y U NO USE GENERICS?! You can and SHOULD use them.
		List recipes = getRecipes();
		recipes.add(craftingRecipeObject);
	}
	
	/**
	 * Adds all recipes. Only called in NModLoader.
	 */
	public static final void addAllRecipes()
	{
		try
		{
			ReflectionManipulateValues.setValue(CraftingManager.class, new CraftingManager(), "instance", modRecipeList);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
		{
			System.err.println("Error adding the recipes!");
			CrashReport crashReport = CrashReport.makeCrashReport(e, "Error adding recipes");
			crashReport.makeCategory("Adding important things necessary for mods to work");
			throw new ReportedException(crashReport);
		}
	}
}
