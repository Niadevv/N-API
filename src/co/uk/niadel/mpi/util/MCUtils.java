package co.uk.niadel.mpi.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class MCUtils
{
	public static Block[] getBlocksRelativeToCoords(World world, int x, int y, int z)
	{
		return new Block[] 
				{
					world.getBlock(x + 1, y, z), //left
					world.getBlock(x - 1, y, z), //right
					world.getBlock(x, y + 1, z), //up
					world.getBlock(x, y - 1, z), //down
					world.getBlock(x, y, z + 1), //forward
					world.getBlock(x, y, z - 1), //backwards
					world.getBlock(x + 1, y + 1, z),//left-up
					world.getBlock(x + 1, y - 1, z),//left-down
					world.getBlock(x - 1, y + 1, z),//right-up
					world.getBlock(x - 1, y - 1, z),//right-down
					world.getBlock(x, y + 1, x + 1),//forwards-up
					world.getBlock(x, y - 1, x + 1),//forwards-down
					world.getBlock(x, y + 1, x - 1),//backwards-up
					world.getBlock(x, y - 1, x - 1)//backwards-down
				};
	}
	
	/**
	 * Gets the coords of a Tile Entity as an int[].
	 * @param theTE
	 * @return
	 */
	public static int[] getCoordsOfTE(TileEntity theTE)
	{
		return new int[] {theTE.field_145848_d, theTE.field_145849_e, theTE.field_145851_c};
	}
	
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
		if (MCData.isWorldServerSide())
		{
			if (!shouldTranslate)
			{
				MinecraftServer.getServer().addChatMessage(new ChatComponentText(message));
			}
			else
			{
				MinecraftServer.getServer().addChatMessage(new ChatComponentTranslation(message));
			}
		}
		else
		{
			if (!shouldTranslate)
			{
				Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
			}
			else
			{
				Minecraft.getMinecraft().thePlayer.sendChatMessage(new ChatComponentTranslation(message).getUnformattedTextForChat());
			}
		}
	}
	
	/**
	 * Plays a sound.
	 * @param sound
	 */
	public static final void playSound(ISound sound)
	{
		Minecraft.getMinecraft().getSoundHandler().playSound(sound);
	}
	
}
