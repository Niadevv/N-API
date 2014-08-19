package co.uk.niadel.mpi.util;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

/**
 * Used to get things like the world object and whether or not it is the client side or not.
 * @author Niadel
 *
 */
public final class MCData
{
	/**
	 * Whether or not the client is Forge. False by default. Only set by NAPIMod in the Forge wrapper.
	 */
	public static boolean isForge = false;
	
	/**
	 * Uses threads as Server side and Client side run in different threads.
	 */
	public static final boolean isServerSide()
	{
		if (Thread.currentThread().getName().equalsIgnoreCase("Server thread"))
		{
			return true;
		}
		else
		{
			return Minecraft.getMinecraft() == null;
		}
	}
	
	/**
	 * Returns the opposite of isServerSide()
	 */
	public static final boolean isClientSide()
	{
		return !isServerSide();
	}
	
	/**
	 * Gets whether or not the game is obfuscated.
	 * @return Whether or not the game is obfuscated.
	 */
	public static final boolean isGameObfed()
	{
		try
		{
			Class.forName("bnq");
			return true;
		}
		catch (ClassNotFoundException e)
		{
			return false;
		}
	}
	
	/**
	 * Returns whether or not the client is primarily dominated by Forge and the Forge Wrapper is active.
	 */
	public static final boolean isForgeDominated()
	{
		return isForge;
	}
	
	/**
	 * Returns whether or not the client is primarily dominated by N-API.
	 */
	public static final boolean isNAPIDominated()
	{
		return !isForgeDominated();
	}
	
	/**
	 * Gets the current world's name.
	 * @return The world's name.
	 */
	public static final String getWorldName()
	{
		if (isServerSide())
		{
			//Server side
			return MinecraftServer.getServer().getWorldName();
		}
		else
		{
			//Client side
			return Minecraft.getMinecraft().theWorld.getWorldInfo().getWorldName();
		}
	}
	
	/**
	 * Used by the loader. Unique method that is ASMd to be this exact thing to avoid tampering when ASM is initialised.
	 * @return The fully qualified name of the NAPI Mod Register.
	 */
	public static final String getNAPIRegisterClass()
	{
		return "co.uk.niadel.mpi.modhandler.ModRegister";
	}
}
