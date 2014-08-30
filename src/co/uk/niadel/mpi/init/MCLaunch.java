package co.uk.niadel.mpi.init;

import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;
import net.minecraft.client.main.Main;

/**
 * Patches then launches Minecraft.
 */
public class MCLaunch
{
	@Internal
	private static final MCGamePatcher mcGamePatcher = new MCGamePatcher();

	public static void main(String[] args)
	{
		mcGamePatcher.manipulateBytecodes(ObfedClassNames.BOOTSTRAP);
		Main.main(args);
	}
}
