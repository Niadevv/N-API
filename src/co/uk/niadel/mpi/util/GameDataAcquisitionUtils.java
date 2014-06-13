package co.uk.niadel.mpi.util;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

/**
 * Used to get things like the world object and whether or not it is the client side or not.
 * @author Niadel
 *
 */
public class GameDataAcquisitionUtils
{
	/**
	 * Whether or not the client is Forge. False by default. Only set by NAPIMod in the Forge wrapper.
	 */
	public static boolean isForge = false;
	
	/**
	 * Gets the World object. Note, I've heard the world gotten from Minecraft.getMinecraft() is client side
	 * only, so this may be potentially useless. It's best to avoid using this when possible.
	 * @return
	 */
	public static final World getWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
	
	/**
	 * Uses threads as Server side and Client side run in different threads.
	 */
	public static final boolean isWorldServerSide()
	{
		return Thread.currentThread().getName().equalsIgnoreCase("server thread");	
	}
	
	/**
	 * Returns the opposite of isWorldServerSide()
	 */
	public static final boolean isWorldClientSide()
	{
		return !isWorldServerSide();
	}
	
	/**
	 * Gets the side of the specified world.
	 * @param world
	 */
	public static final String getWorldSide(World world)
	{
		if (world.isClient)
		{
			return "Client";
		}
		else
		{
			return "Server";
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
	 * @return
	 */
	public static final String getWorldName()
	{
		if (isWorldServerSide())
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
}
