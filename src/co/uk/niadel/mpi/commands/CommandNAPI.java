package co.uk.niadel.mpi.commands;

import co.uk.niadel.mpi.modhandler.loadhandler.Mod;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.Iterator;

/**
 * Command for getting infos about N-API from in game.
 */
public class CommandNAPI extends CommandBase
{
	public String getCommandName()
	{
		return "N-API";
	}

	public void processCommand(ICommandSender sender, String[] commandArgs)
	{
		if (commandArgs[0].equalsIgnoreCase("listMods"))
		{
			Iterator<Mod> modIterator = NModLoader.mods.iterator();

			while (modIterator.hasNext())
			{
				Mod nextMod = modIterator.next();
				sender.addChatMessage(new ChatComponentText("Mod: " + nextMod.getModId() + " Version: " + nextMod.getVersion()));
			}
		}
		else if (commandArgs[0].equalsIgnoreCase("modCount"))
		{
			sender.addChatMessage(new ChatComponentText("N-API Mods Count: " + String.valueOf(NModLoader.mods.getModCount())));
		}
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "NO.COMMAND.USAGE.YET";
	}
}
