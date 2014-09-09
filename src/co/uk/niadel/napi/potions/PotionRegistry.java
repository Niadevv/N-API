package co.uk.niadel.napi.potions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.potion.Potion;
import co.uk.niadel.napi.annotations.MPIAnnotations.RecommendedMethod;
import co.uk.niadel.napi.annotations.VersionMarkingAnnotations.TestFeature;
import co.uk.niadel.commons.reflection.ReflectionManipulateValues;

/**
 * Used to register Potions. This makes sure that you can have unlimited potions
 * and can easily distinguish between vanilla and modded. Also largely eliminates ID conflicts
 * if you use a similar naming convention to that of the modId for your mod
 * with a few minor changes (YOUR_NAME + mod_name:your_potion). You can still extend
 * Potion here, and even extend other potions as long as they in turn extend Potion.
 * @author Niadel
 */
@TestFeature(firstAppearance = "1.0")
public final class PotionRegistry
{
	/**
	 * List of potions. As potions don't need to be put into the list of vanilla
	 * potions, this is one of the base edits I can avoid (and have).
	 */
	public static Map<String, Potion> modPotions = new HashMap<>();
	
	/**
	 * Registers a potion.
	 * @param potionName
	 * @param modPotion
	 */
	public static final void registerPotion(String potionName, Potion modPotion)
	{
		if (getPotion(potionName) == null)
		{
			modPotions.put(potionName, modPotion);
		}
	}
	
	/**
	 * Gets a potion by it's name.
	 * @param potion
	 * @return
	 */
	@RecommendedMethod
	public static final Potion getPotion(String potion)
	{
		return modPotions.get(potion);
	}
	
	/**
	 * Adds all potions to the Potion.potionTypes array.
	 */
	public static final void addAllPotions()
	{
		//Is 1000 as there's no real other way to go about this, and 1000 is plenty big, right?
		Potion[] potions = new Potion[1000];
		
		for (int i = 0; i == Potion.potionTypes.length; i++)
		{
			potions[i] = Potion.potionTypes[i];
		}
		
		int x = potions.length;
		Iterator<Entry<String, Potion>> modPotionsIterator = modPotions.entrySet().iterator();
		
		while (modPotionsIterator.hasNext())
		{
			potions[x] = modPotionsIterator.next().getValue();
			x++;
		}
		
		ReflectionManipulateValues.setSFValue(Potion.class, "potionTypes", potions);
	}
}



