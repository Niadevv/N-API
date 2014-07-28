package co.uk.niadel.mpi.common;

/**
 * Used to get info about the running version of N-API. Versioning follows Minecraft's
 * versioning conventions: majorversion.minorversion.bugfixversion
 * with the versions all capable of going up as high as possible, and don't follow decimal
 * logic.
 * @author Niadel
 *
 */
public interface NAPIData
{
	/**
	 * N-API's name.
	 */
	public static final String NAME = "N-API";
	
	/**
	 * N-API's mod id.
	 */
	public static final String MODID = "NIADEL_n_api";
	
	/**
	 * N-API's major version, increments every major overhaul after release.
	 */
	public static final String MAJOR_VERSION = "0";
	
	/**
	 * N-API's minor version, increments every update that just adds new features.
	 */
	public static final String MINOR_VERSION = "0";
	
	/**
	 * N-API's bug fix version, increments every time I fix a "Nia-Derp" or other bug.
	 */
	public static final String BUGFIX_VERSION = "0";
	
	/**
	 * The purely numeric version of N-API. Only used in ModRegister.
	 */
	public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + BUGFIX_VERSION;
	
	/**
	 * The version of Minecraft N-API is for.
	 */
	public static final String MC_VERSION = "1.7.10";
	
	/**
	 * Changes depending on what state of development this version is for. Values I plan it to be: 
	 * alpha, beta, release, snapshot-[whateversnapshotnumberitisfollowingMCsnapshotnamingconventions],
	 * alpha-snapshot-[snapshot], beta-snapshot-[snapshot]
	 */
	public static final String STATE = "alpha";
	
	/**
	 * The full version of N-API.
	 */
	public static final String FULL_VERSION = NAME + "-" + MC_VERSION + "-" + STATE + "-" + VERSION;
	
	/**
	 * Fully qualified name of the standard N-API transformer.
	 */
	public static final String NAPI_ASM_TRANSFORMER = "co.uk.niadel.mpi.asm.NAPIASMTransformer";
	
	/**
	 * List of classes used in NAPIASMTransformer.
	 */
	public static final String[] ASMD_CLASSES = {"net.minecraft.item.Item",
			"net.minecraft.client.renderer.entity.RenderItem",
			"net.minecraft.world.gen.structure.StructureVillagePieces",
			"net.minecraft.world.World",
			"net.minecraft.util.Explosion",
			"net.minecraft.world.gen.layer.GenLayerBiome",
			"net.minecraft.entity.EntityLiving",
			"net.minecraft.entity.player.EntityPlayer",
			"co.uk.niadel.mpi.util.MCData",
			"net.minecraft.util.ReportedException",
			"net.minecraft.entity.Entity",
			"net.minecraft.entity.EntityLivingBase"};
	
	/**
	 * List of obfuscated classes used in NAPIASMTransformer.
	 */
	public static final String[] ASMD_CLASSES_OBFD = {"co.uk.niadel.mpi.util.MCData"};
}
