package co.uk.niadel.mpi.achievement;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import co.uk.niadel.mpi.util.reflection.ReflectionCallMethods;
import net.minecraft.stats.Achievement;

public final class AchievementRegistry
{
	/**
	 * Registers an acheivement.
	 * @param theAchievement
	 * @param achievementClass
	 */
	public static void registerAchievement(Achievement theAchievement, Class<? extends Achievement> achievementClass)
	{
		// Reflection stuff for protected method. Use what you were given, eh?
		ReflectionCallMethods.callMethod(achievementClass, "registerStat", new Object[] {theAchievement, achievementClass});
	}
}
