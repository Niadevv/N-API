package co.uk.niadel.napi.init;

import co.uk.niadel.napi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.napi.asm.transformers.NAPIASMDeSysOutTransformer;
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
		NAPIASMDeSysOutTransformer.setEnabled();
		mcGamePatcher.manipulateBytecodes(ObfedClassNames.BOOTSTRAP);
		Main.main(args);
	}
}
