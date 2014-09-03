package co.uk.niadel.napi.commands;

import java.util.Iterator;
import java.util.Map;

import co.uk.niadel.napi.util.MCData;
import net.minecraft.command.ICommand;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;

/**
 * Used to add commands. Much easier than FML's using the server starting event.
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
	 * Registers the command so you don't have to edit the base file. Is somewhat complex as
	 * the register method isn't static.
	 * @param command The command to register.
	 */
	public static void registerModCommand(ICommand command)
	{
		//The check here stops an NPE.
		if (MCData.isServerSide())
		{
			((ServerCommandManager) MinecraftServer.getServer().getCommandManager()).registerCommand(command);
		}
	}
	
	/**
	 * Returns an array of commands containing all the commands, vanilla
	 * or modded.
	 * @return commands
	 */
	public ICommand[] getAllCommands()
	{
		ICommand[] commands = new ICommand[1000];
		Iterator<Map.Entry<String, ICommand>> commandIterator = commandMap.entrySet().iterator();
		int i = 0;
		
		while (commandIterator.hasNext())
		{
			commands[i] = commandIterator.next().getValue();
			i++;
		}
		
		return commands;
	}

	/**
	 * Checks to see if a command is in the whole list.
	 * @param testedCommand The command to check to see if it exists.
	 * @return Whether or not the command exists.
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
		
		//Should be put in an else block, but that causes an error for some stupid reason.
		return false;
	}
}
