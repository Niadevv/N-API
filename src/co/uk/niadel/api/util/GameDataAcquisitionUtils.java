package co.uk.niadel.api.util;

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
	 * Gets the World object.
	 * @return
	 */
	public static final World getWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
	
	/**
	 * Gets whether or not the world is on the client (rendering) side. If this breaks Forge side, blame Lex for apparently renaming
	 * isClient to isRemote, which is incredibly stupid and makes no sense. Or was it CPW who renamed it?
	 * @return
	 */
	public static final boolean isWorldClientSide()
	{
		return getWorld().isClient;	
	}
	
	/**
	 * Returns whether or not the world is on the server (world controlling) side.
	 * @return
	 */
	public static final boolean isWorldServerSide()
	{
		return !isWorldClientSide();
	}
	
	/**
	 * Returns whether or not the side is primarily dominated by Forge and the Forge Wrapper is active.
	 * @return
	 */
	public static final boolean isForgeDominated()
	{
		return isForge;
	}
	
	/**
	 * Returns whether or not the side is primarily dominated by N-API.
	 * @return
	 */
	public static final boolean isNAPIDominated()
	{
		return !isForgeDominated();
	}
	
	public static final String getWorldName()
	{
		return MinecraftServer.getServer().getWorldName();
	}
}
