package co.uk.niadel.api.achievement;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import co.uk.niadel.api.util.reflection.ReflectionCallMethods;
import net.minecraft.stats.Achievement;

public final class AchievementRegistry
{
	public static void registerAchievement(Achievement theAchievement, Class<? extends Achievement> achievementClass)
	{
		// Reflection stuff for protected method. Use what you were given, eh?
		ReflectionCallMethods.callMethod(achievementClass, "registerStat", new Object[] {theAchievement, achievementClass});
	}
}
