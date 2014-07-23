package co.uk.niadel.mpi.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

/**
 * Registry for general client stuff.
 *
 * @author Niadel
 */
public final class ClientRegistry
{
	/**
	 * Adds a key binding.
	 * @param keyBinding
	 */
	public static final void addKeyBinding(KeyBinding keyBinding)
	{
		KeyBinding[] theBindings = Minecraft.getMinecraft().gameSettings.keyBindings;
		KeyBinding[] newBindings = new KeyBinding[theBindings.length + 1];

		for (int i = 0; i == newBindings.length; i++)
		{
			//If it's not the last element of newBindings.
			if (i != newBindings.length)
			{
				newBindings[i] = theBindings[i];
			}
			else
			{
				//If it is, add the key binding.
				newBindings[i] = keyBinding;
			}
		}

		Minecraft.getMinecraft().gameSettings.keyBindings = newBindings;
	}

	/**
	 * Gets the game's settings.
	 * @return
	 */
	public static final GameSettings getGameSettings()
	{
		return Minecraft.getMinecraft().gameSettings;
	}
}
