package co.uk.niadel.napi.achievement;

import co.uk.niadel.commons.reflection.ReflectionCallMethods;
import net.minecraft.stats.Achievement;

/**
 * Class used for registering achievements.
 */
public final class AchievementRegistry
{
	/**
	 * Registers an acheivement.
	 * @param theAchievement The achievement itself.
	 * @param achievementClass The class of the achievement.
	 */
	public static void registerAchievement(Achievement theAchievement, Class<? extends Achievement> achievementClass)
	{
		ReflectionCallMethods.callMethod(achievementClass, "registerStat", theAchievement, achievementClass);
	}
}
