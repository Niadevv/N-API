package co.uk.niadel.napi.world;

import java.util.HashMap;
import java.util.Map;

/**
 * Where gamerules are registered.
 * @author Niadel
 *
 */
public final class GameRuleRegistry 
{
	/**
	 * Contains all game rules from mods, keyed by the game rule name and indexed by the default value.
	 */
	public static Map<String, String> gameRules = new HashMap<>();
	
	/**
	 * Adds a game rule.
	 * @param gameRuleName The name of the game rule.
	 * @param defaultValue The default value of the gamerule.
	 */
	public static final void addGameRule(String gameRuleName, String defaultValue)
	{
		gameRules.put(gameRuleName, defaultValue);
	}
}
