package co.uk.niadel.api.achievement;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import co.uk.niadel.api.reflection.ReflectionCallMethods;
import net.minecraft.stats.Achievement;

public final class AchievementRegistry
{
	public static Map<String, Achievement> modAchievementsMap = new HashMap<>();
	
	public static void registerAchievement(Achievement theAchievement, Class<? extends Achievement> achievementClass)
	{
		try
		{
			ReflectionCallMethods.callMethod(achievementClass, "registerStat", new Object[] {theAchievement, achievementClass});
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
}
