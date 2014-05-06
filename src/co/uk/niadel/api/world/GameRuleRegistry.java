package co.uk.niadel.api.world;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import co.uk.niadel.api.reflection.ReflectionManipulateValues;

/**
 * Where gamerules are registered.
 * @author Niadel
 *
 */
public final class GameRuleRegistry 
{
	public static Map<String, String> gameRules = new HashMap<>();
	
	/**
	 * Adds a game rule.
	 * @param gameRuleName
	 * @param defaultValue
	 */
	public static final void addGameRule(String gameRuleName, String defaultValue)
	{
		gameRules.put(gameRuleName, defaultValue);
	}
	
	public static final void addAllGameRules()
	{
		
	}
}
