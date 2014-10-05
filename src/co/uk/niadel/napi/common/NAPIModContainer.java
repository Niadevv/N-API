package co.uk.niadel.napi.common;

import co.uk.niadel.napi.modhandler.NAPIModRegister;
import co.uk.niadel.napi.nml.ModContainer;
import co.uk.niadel.napi.nml.NModLoader;

import java.io.File;

/**
 * Important for N-API to work.
 *
 * @author Niadel
 */
public class NAPIModContainer extends ModContainer
{
	public NAPIModContainer(NAPIModRegister mod)
	{
		super(mod, NAPIData.MODID, NAPIData.VERSION, new String[0], true, new File(NModLoader.appDataDir.toPath().toString() + "/lib/" + NAPIData.FULL_VERSION + ".jar"));
	}
}
