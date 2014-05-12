package co.uk.niadel.api.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
	 * A UtilityMethods object, because I'm nice.
	 */
	public static UtilityMethods instance = new UtilityMethods();
	
	/**
	 * Constructor to provide access to the 2 important byte manipulation things due to some weird error.
	 */
	public UtilityMethods() {}
	
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
	 * Method used by NModLoader in the Library sub-system of the Mods Dependencies system. Assumes N-API/Minecraft version
	 * convention of majorrelease.update.smallupdate.hotfix. For example, Minecraft 1.7.2 is the second small update
	 * of the seventh update of the first major release.
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
	
	/**
	 * Converts a class to a byte array. Incredibly useful if you want to use ASM as I don't actually know how to recursively
	 * pass byte[]s of vanilla classes without a metric poop ton of code.
	 * @param objectToConvert
	 * @return
	 */
	public static byte[] toByteArray(Object objectToConvert)
	{
		byte[] bytesToReturn = new byte[] {};
		ByteArrayOutputStream byteOutputStream = null;
		ObjectOutputStream objOutputStream = null;
		
		try
		{
			byteOutputStream = new ByteArrayOutputStream();
			objOutputStream = new ObjectOutputStream(byteOutputStream);
			objOutputStream.writeObject(objectToConvert);
			objOutputStream.flush();
			bytesToReturn = byteOutputStream.toByteArray();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				objOutputStream.close();
				byteOutputStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return bytesToReturn;
	}
	
	/**
	 * Converts a byte array to an object.
	 * @param bytesToConvert
	 * @return
	 */
	private Object byteArrayToObjectPrivate(byte[] bytesToConvert)
	{
		Class<?> tempClass = null;
		try
		{
			DummyClassLoader loader = new DummyClassLoader();
			tempClass = loader.dummyDefineClass(null, bytesToConvert, 0, bytesToConvert.length);
			return tempClass.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		return tempClass;
	}
	
	/**
	 * Converts a byte array to an object.
	 * @param bytesToConvert
	 * @return
	 */
	public static final Object byteArrayToObject(byte[] bytesToConvert)
	{
		return instance.byteArrayToObjectPrivate(bytesToConvert);
	}
	
	/**
	 * Converts a byte array to a class.
	 * @param bytesToConvert
	 * @return
	 */
	private Class<?> byteArrayToClassPrivate(byte[] bytesToConvert)
	{
		Class<?> tempClass = null;
		DummyClassLoader loader = new DummyClassLoader();
		tempClass = loader.dummyDefineClass(null, bytesToConvert, 0, bytesToConvert.length);
		return tempClass;
	}
	
	public static final Class<?> byteArrayToClass(byte[] bytesToConvert)
	{
		return instance.byteArrayToClassPrivate(bytesToConvert);
	}
	
	/**
	 * Just a dummy loader for byteArrayToObject and byteArrayToClass.
	 * @author Niadel
	 *
	 */
	private class DummyClassLoader extends ClassLoader
	{
		public DummyClassLoader()
		{
			super();
		}
		
		public Class<?> dummyDefineClass(String name, byte[] bytes, int offset, int length)
		{
			return super.defineClass(name, bytes, offset, length);
		}
	}
}
