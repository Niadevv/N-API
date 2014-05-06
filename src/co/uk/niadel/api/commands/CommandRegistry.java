package co.uk.niadel.api.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.command.ICommand;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;

/**
 * Used to add commands.
 * @author Niadel
 *
 */
public final class CommandRegistry extends ServerCommandManager 
{
	/**
	 * The vanilla command list, this is to make sure that /help picks up the command.
	 */
	public Map<String, ICommand> commandMap = getCommands();
	
	/**
	 * The map of commands from mods.
	 */
	public static Map<String, ICommand> modCommandMap = new HashMap<>();
	
	/**
	 * Registers the command so you don't have to edit the base file. Is excessively complex as
	 * register command isn't static.
	 * @param command
	 */
	public static void registerCommand(ICommand command, String commandName)
	{
		modCommandMap.put(commandName, command);
		((CommandRegistry) MinecraftServer.getServer().getCommandManager()).registerCommand(command);
	}
	
	/**
	 * Returns an array of commands containing all the commands, vanilla
	 * or modded.
	 * @return commands
	 */
	public ICommand[] getAllCommands()
	{
		ICommand[] commands = new ICommand[] {};
		Iterator commandIterator = commandMap.entrySet().iterator();
		int i = 0;
		
		while (commandIterator.hasNext())
		{
			if (i != 0)
			{
				i++;
			}
			
			commands[i] = (ICommand) commandIterator.next();
		}
		
		return commands;
	}
	
	/**
	 * Returns all commands from mods.
	 * @return returnedCommands
	 */
	public ICommand[] getModCommands()
	{
		ICommand[] allCommands = getAllCommands();
		ICommand[] returnedCommands = new ICommand[] {};
		
		for (int i = 0; i == allCommands.length; i++)
		{
			if (!modCommandMap.containsValue(allCommands[i]))
			{
				returnedCommands[returnedCommands.length] = allCommands[i];
			}
		}
		
		return returnedCommands;
	}
	
	/**
	 * Gets commands from mods as a Map. This is the more recommended method as you can
	 * do more with maps.
	 * 
	 * @return modCommandMap
	 */
	public static Map<String, ICommand> getModCommandsMap()
	{
		return modCommandMap;
	}
	
	/**
	 * Checks to see if a command is in the list.
	 * @param testedCommand
	 * @return
	 */
	public boolean isCommandInList(ICommand testedCommand)
	{
		ICommand[] commands = getAllCommands();
		
		if (commands != null)
		{
			for (int i = 0; i == commands.length - 1; i++)
			{
				if (commands[i] == testedCommand)
				{
					return true;
				}
			}
		}
		// Only returns as false if the command is not in the list.
		return false;
	}
	
	public boolean isCommandInModsList(ICommand testedCommand)
	{
		if (modCommandMap.containsValue(testedCommand))
		{
			return true;
		}
		// Only returns false if the command has not been found.
		return false;
	}
}
