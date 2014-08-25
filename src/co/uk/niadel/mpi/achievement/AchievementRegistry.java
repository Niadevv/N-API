package co.uk.niadel.mpi.achievement;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import co.uk.niadel.mpi.util.reflection.ReflectionCallMethods;
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
