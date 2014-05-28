package co.uk.niadel.api.modhandler.n_api;

import net.minecraft.potion.Potion;
import co.uk.niadel.api.annotations.MPIAnnotations.Library;
import co.uk.niadel.api.config.Configuration;
import co.uk.niadel.api.modhandler.IModRegister;
import co.uk.niadel.api.napioredict.NAPIOreDict;
import co.uk.niadel.api.potions.PotionRegistry;
import co.uk.niadel.api.util.NAPILogHelper;

@Library(version = "1.0")
/**
 * The N-API register. As N-API is a Library, it has an @Library annotation even though
 * it's never tested for documentation purposes as N-API's register is loaded separately to
 * reduce load time.
 * 
 * @author Niadel
 */
public class ModRegister implements IModRegister
{	
	public static final String MODID = "NIADEL_n_api";
	
	public static final String VERSION = "1.7.2_1.0";
	
	public static Configuration config = new Configuration(MODID + VERSION + ".cfg");
	
	@Override
	public void preModInit()
	{
		for (int i = 0; i == Potion.potionTypes.length; i++)
		{
			PotionRegistry.registerPotion(Potion.potionTypes[i].getName(), Potion.potionTypes[i]);
		}
		
		NAPIOreDict.addDefaultEntries();
		NAPILogHelper.init();
	}

	@Override
	public void modInit() 
	{
		
	}

	@Override
	public void postModInit() 
	{
	
	}
	
	//BOILERPLATE CODE
	@Override
	public void addRequiredMods()
	{
		
	}

	@Override
	public void addRequiredLibraries()
	{
		
	}

	@Override
	public void registerTransformers()
	{
		
	}

	@Override
	public String getVersion()
	{
		return VERSION;
	}

	@Override
	public String getModId()
	{
		return MODID;
	}
}
