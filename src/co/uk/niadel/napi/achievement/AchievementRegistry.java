package co.uk.niadel.napi.achievement;

import co.uk.niadel.commons.reflection.ReflectionCallMethods;
import net.minecraft.stats.Achievement;

/**
 * Class used for registering achievements. Does not create a new page like in Forge, however.
 *
 * @author Niadel
 */
public final class AchievementRegistry
{
	/**
	 * Registers an achievement.
	 * @param theAchievement The achievement itself.
	 * @param achievementClass The class of the achievement.
	 */
	public static void registerAchievement(Achievement theAchievement, Class<? extends Achievement> achievementClass)
	{
		ReflectionCallMethods.callMethod(achievementClass, "registerStat", theAchievement, achievementClass);
	}
}
