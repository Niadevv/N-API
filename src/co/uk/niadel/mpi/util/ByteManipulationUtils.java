package co.uk.niadel.mpi.util;

import java.io.*;

public final class ByteManipulationUtils
{
	private static final ByteManipulationUtils instance = new ByteManipulationUtils();
	
	/**
	 * Converts a class to a byte array.
	 * @param objectToConvert The object to convert to bytes.
	 * @return The bytes of objectToConvert
	 */
	public static final byte[] toByteArray(Object objectToConvert)
	{
		InputStream stream = objectToInputStream(objectToConvert);

		if (stream == null)
		{
			return new byte[0];
		}

		//Code borrowed and modified from https://forums.bukkit.org/threads/tutorial-extreme-beyond-reflection-asm-replacing-loaded-classes.99376/
		//Edits include fixing the formatting to my taste, along with a few renames, and making the error print
		//to the NAPI Log Helper, as well as fixing a bit of bad programming style.

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		try
		{
			int bytesRead;
			byte[] data = new byte[16384];

			while ((bytesRead = stream.read(data, 0, data.length)) != -1)
			{
				buffer.write(data, 0, bytesRead);
			}

			buffer.flush();
		}
		catch (IOException e)
		{
			NAPILogHelper.logError("Unable to convert " + objectToConvert.getClass().getName() + " to a byte array!");
			e.printStackTrace();
		}

		return buffer.toByteArray();
	}

	/**
	 * Converts an object into an input stream.
	 * @param theObject The object to convert into an input stream.
	 * @return The InputStream object of theObject
	 */
	public static final InputStream objectToInputStream(Object theObject)
	{
		try
		{
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);


			objectOutputStream.writeObject(theObject);

			objectOutputStream.flush();
			objectOutputStream.close();

			return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		}
		catch (IOException e)
		{
			NAPILogHelper.log("Error converting object " + theObject.getClass().getName() + " to an input stream!");
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * Converts a byte array to an object.
	 * @param bytesToConvert
	 * @return
	 */
	private final <X> X byteArrayToObjectPrivate(byte[] bytesToConvert)
	{
		X returnedObj = null;
		try
		{
			DummyClassLoader loader = new DummyClassLoader(ByteManipulationUtils.class.getClassLoader());
			Class<? extends X> tempClass = (Class<? extends X>) loader.dummyDefineClass(null, bytesToConvert, 0, bytesToConvert.length);
			return tempClass.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
			NAPILogHelper.logError(e);
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
	private final <X> Class<? extends X> byteArrayToClassPrivate(byte[] bytesToConvert)
	{
		Class<? extends X> tempClass = null;
		DummyClassLoader loader = new DummyClassLoader(ByteManipulationUtils.class.getClassLoader());
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
	 * Just a dummy loader for byteArrayToObject and byteArrayToClass.
	 * @author Niadel
	 *
	 */
	private final class DummyClassLoader extends ClassLoader
	{
		public DummyClassLoader(ClassLoader parent)
		{
			super(parent);
		}
		
		public Class<?> dummyDefineClass(String name, byte[] bytes, int offset, int length)
		{
			return super.defineClass(name, bytes, offset, length);
		}
	}
}
