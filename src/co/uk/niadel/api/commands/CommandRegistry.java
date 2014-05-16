package co.uk.niadel.api.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.command.ICommand;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import co.uk.niadel.api.annotations.MPIAnnotations.RecommendedMethod;

/**
 * Used to add commands. I don't think Forge does this, or if it does, it's incredibly hard to find.
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
		((ServerCommandManager) MinecraftServer.getServer().getCommandManager()).registerCommand(command);
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
		
		for (ICommand command : allCommands)
		{
			if (!modCommandMap.containsValue(command))
			{
				returnedCommands[returnedCommands.length] = command;
			}
		}
		
		return returnedCommands;
	}
	
	@RecommendedMethod
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
			for (ICommand command : commands)
			{
				if (command == testedCommand)
				{
					return true;
				}
			}
		}
		// Only returns as false if the command is not in the list.
		return false;
	}
	
	/**
	 * Gets whether or not the specified command is in the mods list.
	 * @param testedCommand
	 * @return
	 */
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
