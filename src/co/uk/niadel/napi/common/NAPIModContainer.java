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
		super(mod, NAPIData.MODID, NAPIData.VERSION, null, true, new File(NModLoader.libDir.getPath() + NAPIData.PATH_TO_NAPI_LIB));
	}
}
