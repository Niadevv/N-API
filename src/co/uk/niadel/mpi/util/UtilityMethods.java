package co.uk.niadel.mpi.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

/**
 * The class that contains multiple utility methods for things like removing all blocks
 * in the same collumn of the same type, etc. Uses generics INCREDIBLY heavily to make for maximum
 * usability.
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
	 * Constructor to provide access to the 2 important byte manipulation things due to some weird foshizzles.
	 */
	public UtilityMethods() {}
	
	/**
	 * Converts 3 RGB values to a true RGB number. This means you don't have to spend ages calculating the RGB.
	 * @param red
	 * @param green
	 * @param blue
	 * @return
	 */
	public static final int convertToRGBColour(int red, int green, int blue)
	{
		return ((red << 16) + (green << 8) + blue);
	}
	
	public static final int getNumberNotInList(List<Integer> listOfNumsToExclude, int maxNum)
	{
		Random rand = new Random();
		
		int testedId = rand.nextInt(maxNum);
		
		if (!listOfNumsToExclude.contains(testedId))
		{
			return testedId;
		}
		else
		{
			return -1;
		}
	}
	
	/**
	 * Copies an array as I have no idea how to use the Java util class Arrays o_O
	 * @param arrayToCopy
	 * @return
	 */
	public static final <X, Y> X[] copyArray(Y[] arrayToCopy)
	{
		Object[] arrayCopy = new Object[] {};
		
		int i = 0;
		
		for (Y currItem : arrayToCopy)
		{
			arrayCopy[i] = currItem;
		}
		
		return (X[]) arrayCopy;
	}
	
	/**
	 * Tests if the specified array contains the specified value.
	 * @param array
	 * @param valueToTest
	 * @return Whether array contains valueToTest.
	 */
	public static final <X> boolean doesArrayContainValue(X[] array, X valueToTest)
	{
		if (Arrays.asList(array).contains(valueToTest))
		{
			return true;
		}
		else
		{
			return false;
		}
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
		if (!shouldTranslate)
		{
			MinecraftServer.getServer().addChatMessage(new ChatComponentText(message));
		}
		else
		{
			MinecraftServer.getServer().addChatMessage(new ChatComponentTranslation(message));
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
	public static <X> byte[] toByteArray(X objectToConvert)
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
			objOutputStream.close();
			byteOutputStream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return bytesToReturn;
	}
	
	/**
	 * Converts a byte array to an object.
	 * @param bytesToConvert
	 * @return
	 */
	private <X> X byteArrayToObjectPrivate(byte[] bytesToConvert)
	{
		X returnedObj = null;
		try
		{
			DummyClassLoader loader = new DummyClassLoader();
			Class<? extends X> tempClass = (Class<? extends X>) loader.dummyDefineClass(null, bytesToConvert, 0, bytesToConvert.length);
			return (X) tempClass.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		return returnedObj;
	}
	
	/**
	 * Converts a byte array to an object.
	 * @param bytesToConvert
	 * @return
	 */
	public static final <X> X byteArrayToObject(byte[] bytesToConvert)
	{
		return instance.byteArrayToObjectPrivate(bytesToConvert);
	}
	
	/**
	 * Converts a byte array to a class. Is private due to non-staticness.
	 * @param bytesToConvert
	 * @return
	 */
	private <X> Class<? extends X> byteArrayToClassPrivate(byte[] bytesToConvert)
	{
		Class<? extends X> tempClass = null;
		DummyClassLoader loader = new DummyClassLoader();
		tempClass = (Class<? extends X>) loader.dummyDefineClass(null, bytesToConvert, 0, bytesToConvert.length);
		return tempClass;
	}
	
	/**
	 * Converts a byte array to a class.
	 * @param bytesToConvert
	 * @return
	 */
	public static final <X> Class<? extends X> byteArrayToClass(byte[] bytesToConvert)
	{
		return instance.byteArrayToClassPrivate(bytesToConvert);
	}
	
	/**
	 * Plays a sound.
	 * @param sound
	 */
	public static final void playSound(ISound sound)
	{
		Minecraft.getMinecraft().getSoundHandler().playSound(sound);
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
