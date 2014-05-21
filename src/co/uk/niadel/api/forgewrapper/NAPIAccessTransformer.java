package co.uk.niadel.api.forgewrapper;

import java.io.IOException;
import cpw.mods.fml.common.asm.transformers.AccessTransformer;

public class NAPIAccessTransformer extends AccessTransformer
{
	public NAPIAccessTransformer() throws IOException
	{
		super("n_api_at.cfg");
	}
}
