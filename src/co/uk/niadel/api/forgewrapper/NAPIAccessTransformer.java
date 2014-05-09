package co.uk.niadel.api.forgewrapper;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

public class NAPIAccessTransformer extends AccessTransformer
{
	public NAPIAccessTransformer()
	{
		super("n_api_at.cfg");
	}
}
