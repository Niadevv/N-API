package co.uk.niadel.napi.commands;

import co.uk.niadel.napi.annotations.DocumentationAnnotations.Temprorary;
import co.uk.niadel.napi.nml.IModContainer;
import co.uk.niadel.napi.nml.NModLoader;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import java.util.Iterator;

/**
 * Command for getting infos about N-API from in game.
 *
 * @author Niadel
 */
@Temprorary(versionToBeRemoved = "The version I add a mods list GUI.")
public class CommandNAPI extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "N-API";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] commandArgs)
	{
		if (commandArgs[0].equalsIgnoreCase("listmods"))
		{
			Iterator<IModContainer> modIterator = NModLoader.mods.iterator();

			while (modIterator.hasNext())
			{
				IModContainer nextMod = modIterator.next();
				sender.addChatMessage(new ChatComponentText("Mod: " + nextMod.getModId() + " Version: " + nextMod.getVersion()));
			}
		}
		else if (commandArgs[0].equalsIgnoreCase("modcount"))
		{
			sender.addChatMessage(new ChatComponentText(new ChatComponentTranslation("napi.commands.napi.modcount.success").getFormattedText() + String.valueOf(NModLoader.mods.getModCount())));
		}
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "napi.commands.napi.usage";
	}
}
