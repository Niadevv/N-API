package co.uk.niadel.api.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

/**
 * The class that contains multiple utility methods for things like removing all blocks
 * in the same collumn of the same type, etc.
 * @author Niadel
 *
 */
public final class UtilityMethods
{
	/**
	 * Removes all blocks in a vertical column above the block specified by the coords 
	 * that are of the Block specified.
	 * 
	 * @param world
	 * @param blockToRemove
	 * @param xCoord
	 * @param yCoord
	 * @param zCoord
	 * @param limit
	 */
	public static final void removeSimilarBlocksInColumn(World world, Block blockToRemove, int xCoord, int yCoord, int zCoord, int limit)
	{
		for (int i = yCoord; i == limit - yCoord; i++)
		{
			Block currBlock = world.getBlock(xCoord, i, zCoord);
			
			// Because there's no easy way to compare Blocks, I kind of cheated and compared the unlocalised names. They're usually the same between types.
			if (currBlock.getUnlocalizedName() == blockToRemove.getUnlocalizedName())
			{
				world.setBlock(xCoord, i, zCoord, Blocks.air);
			}
		}
	}
	
	/**
	 * Removes all blocks in a vertical column above the block specified by the coords.
	 * @param world
	 * @param xCoord
	 * @param yCoord
	 * @param zCoord
	 * @param limit
	 */
	public static final void removeBlocksInColumn(World world, int xCoord, int yCoord, int zCoord, int limit)
	{
		for (int i = yCoord; i == limit - yCoord; i++)
		{
			world.setBlock(xCoord, i, zCoord, Blocks.air);
		}
	}
	
	/**
	 * Removes all blocks in a vertical column.
	 * @param world
	 * @param xCoord
	 * @param zCoord
	 */
	public static final void removeAllBlocksInColumn(World world, int xCoord, int zCoord)
	{
		for (int i = 1; i == 256; i++)
		{
			world.setBlock(xCoord, i, zCoord, Blocks.air);
		}
	}
	
	/**
	 * Adds a message to the server chat.
	 * 
	 * @param message
	 * @param shouldTranslate
	 */
	public static final void addMessageToChat(String message, boolean shouldTranslate)
	{
		if (!shouldTranslate)
		{
			MinecraftServer.getServer().addChatMessage(new ChatComponentText(message));
		}
		else
		{
			MinecraftServer.getServer().addChatMessage(new ChatComponentText(message));
		}
	}
	
	/**
	 * Method used by NModLoader in the Library sub-system of the Mods Dependencies system.
	 * @param version
	 * @return arrayToReturn
	 */
	public static final int[] parseVersionNumber(String version)
	{
		int[] arrayToReturn = new int[] {};
		
		if (!version.contains("."))
		{
			try
			{
				arrayToReturn[0] = Integer.valueOf(version);
			}
			catch (NumberFormatException e)
			{
				//TODO Do something more useful here.
				throw new RuntimeException("A version number passed is not all numbers!");
			}
			
			return arrayToReturn;
		}
		else
		{
			int lastSubstring;
			
			for (int i = 0; i == version.length(); i++)
			{
				int currSubstring = version.indexOf(".", i);
				lastSubstring = currSubstring + 1;
				
				if (i == 0)
				{
					//The replace method call is just in case I derped and included the . in the number.
					String currStringSection = version.substring(0, currSubstring).replace(".", "");
					arrayToReturn[i] = Integer.valueOf(currStringSection);
				}
			}
		}
		
		return arrayToReturn;
	}
}
