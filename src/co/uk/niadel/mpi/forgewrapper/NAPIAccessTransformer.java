package co.uk.niadel.mpi.forgewrapper;

import java.io.IOException;
import cpw.mods.fml.common.asm.transformers.AccessTransformer;

public final class NAPIAccessTransformer extends AccessTransformer
{
	public NAPIAccessTransformer() throws IOException
	{
		super("n_api_at.cfg");
	}
}
