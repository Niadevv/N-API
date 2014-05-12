package co.uk.niadel.api.forgewrapper;

import java.util.Map;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.*;

@Name(value = "N-API Forge Wrapper ASM Transformers and Access Transformers")
@MCVersion(value = "1.7.2")
public class NAPILoadingPlugin implements IFMLLoadingPlugin
{
	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] {"co.uk.niadel.api.forgewrapper.ASMPatcher"};
	}

	@Override
	public String getModContainerClass()
	{
		return null;
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
		
	}

	@Override
	public String getAccessTransformerClass()
	{
		return "co.uk.niadel.api.forgewrapper.NAPIAccessTransformer";
	}
}
