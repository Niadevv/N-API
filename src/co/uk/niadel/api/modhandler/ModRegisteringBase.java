package co.uk.niadel.api.modhandler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Deprecated
/**
 * Technically should be called PluginRegisteringBase, but, whatever.
 * @deprecated You used to have to extend this class to have your mod be loaded, but now you must implement IModRegister.
 * @author Niadel
 */
public class ModRegisteringBase
{
	/**
	 * The display name of the mod, exclusive to that mod only.
	 */
	private String modName;
	
	/**
	 * The mod name used internally by the loader. Should follow a Pythonic
	 * naming convention with the author name in capitals prefixing it (eg. 
	 * YOU_your_mod, NIADEL_n_api, SIRSENGIR_buildcraft (at least I 
	 * think that's who made buildcraft), LEXMANOS_forge (not that LexManos would 
	 * use this to make Forge with, that'd be more ridiculous than aliens that forbid
	 * the wearing of clothing :P.)) for other modders intending to make parts of mods 
	 * dependent on your mod.
	 */
	private String modInternalName;
	
	/**
	 * The current version of the mod.
	 */
	private String currentModVersion;
	
	/**
	 * Map that is indexed with the modInternalName, and contains an
	 * array of classes specific to the mod, which is labelled by the index.
	 */
	public static Map<String, Class[]> modsList = new HashMap<>();
	
	public final void registerMod(String modInternalName, String currModVersion, String modName, Class[] classes)
	{
		this.modInternalName = modInternalName;
		this.currentModVersion = currModVersion;
		this.modName = modName;
		
		modsList.put(modInternalName, classes);
	}
	
	/**
	 * Evaluates whether the class file already exists for another mod.
	 * Returns true if valid and vice versa.
	 */
	private final boolean evaluateModFiles(Class[] modFiles)
	{
		if (modsList.isEmpty() == true)
		{
			return true;
		}
		
		Iterator modsListIterator = modsList.entrySet().iterator();
		
		while (modsListIterator.hasNext())
		{
			Object modClass = ((Iterator) modsList).next();
			
			if (modsList.containsValue(modClass) == true)
			{
				return false;
			}	
		}
		return true;
	}

	public static final Map<String, Class[]> getModsList()
	{
		return modsList;
	}
}
