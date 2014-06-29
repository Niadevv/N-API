package co.uk.niadel.mpi.asm;

import java.util.Map;
import co.uk.niadel.mpi.util.ByteManipulationUtils;
import co.uk.niadel.mpi.util.NAPILogHelper;

public abstract class ASMTransformer
{
	/**
	 * Gets the bytes of the specified class.
	 * @param theClass - The fully qualified name (or binary name) of the class to get the bytes
	 * of.
	 * @return
	 */
	public byte[] getBytesOfClass(String theClassName)
	{
		try
		{
			Class<?> theClass = Class.forName(theClassName);
			return ByteManipulationUtils.toByteArray(theClass);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			NAPILogHelper.logError("The class " + theClassName + " does NOT exist! If this is "
					+ "a mod class, check it exists first with NModLoader.doesModExist()! If it's "
					+ "a Minecraft class, check your spelling! Note that it may be different if running"
					+ " outside of your IDE as the non-IDE Minecraft files are obfuscated.");
			return null;
		}
	}
	
	/**
	 * Where you manipulate the bytecodes themselves - passedBytes is a Map of the
	 * byte[]s of the classes you requested in requestTransformedClasses.
	 * @param passedBytes
	 * @return The modified bytes map
	 * 
	 */
	public abstract Map<String, Byte[]> manipulateBytecodes(Map<String, Byte[]> passedBytes);
	
	/**
	 * Necessary for the transformer to work, this is where you tell the loader that
	 * you're transforming these classes by returning the fully qualified names of them:
	 * 
	 * Eg. net.minecraft.block.Block, 
	 * net.minecraft.entity.Entity, etc.
	 * @return
	 */
	public abstract String[] requestTransformedClasses();
}
