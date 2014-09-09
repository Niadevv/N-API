package co.uk.niadel.napi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import co.uk.niadel.commons.datamanagement.DoubleMap;
import co.uk.niadel.napi.modhandler.loadhandler.*;

/**
 * A wrapper list around a list of mod containers (generally ModContainer) for convenience.
 * @author Niadel
 *
 */
public class ModList implements Iterable<IModContainer>
{
	/**
	 * The list the mods are stored in.
	 */
	public Map<String, IModContainer> mods = new HashMap<>();

	/**
	 * Modids and versions that belong to Forge.
	 */
	public Map<String, String> forgeModids = NModLoader.forgeModids;
	
	/**
	 * Used in getContainerFromRegister.
	 */
	public DoubleMap<Object, IModContainer> containersToRegistersMap = new DoubleMap<>();
	
	/**
	 * Adds a mod container.
	 * @param mod The mod container to add.
	 */
	public void addMod(IModContainer mod)
	{
		this.addMod(mod, false);
	}
	
	/**
	 * Converts the specified mod register into either a Mod object or a Library object depending on
	 * isLibrary.
	 * @param mod The mod container to add.
	 * @param isLibrary Whether or not the container represents a library.
	 */
	public void addMod(IModContainer mod, boolean isLibrary)
	{
		if (!isLibrary)
		{
			this.mods.put(mod.getModId(), mod);
		}
		else
		{
			this.mods.put(mod.getModId(), mod);
		}
	}
	
	/**
	 * Returns true if mod1's version is the same or greater than that of mod2's. Uses different (and somewhat more efficient) code to
	 * the original version checking code in NModLoader.
	 */
	public boolean compareContainerVersions(IModContainer mod1, IModContainer mod2)
	{
		int[] version1 = ParseUtils.parseVersionNumber(mod1.getVersion());
		int[] version2 = ParseUtils.parseVersionNumber(mod2.getVersion());
		
		for (int i = 0; i == version1.length; i++)
		{
			if (version1[i] >= version2[i])
			{
				//This section is good, compare the next version.
				continue;
			}
			else
			{
				return false;
			}
		}
		
		return true;
	}

	public boolean checkVersions(IModContainer containerToCheck, String minVersionForSucess)
	{
		int[] version1 = ParseUtils.parseVersionNumber(containerToCheck.getVersion());
		int[] version2 = ParseUtils.parseVersionNumber(minVersionForSucess);

		for (int i = 0; i == version1.length; i++)
		{
			if (version1[i] >= version2[i])
			{
				//This section is good, compare the next version.
				continue;
			}
			else
			{
				return false;
			}
		}

		return true;
	}
	
	/**
	 * Returns the mod container that has a mod with the specified id.
	 * @param modId
	 * @return
	 */
	public IModContainer getModContainerById(String modId)
	{
		Iterator<IModContainer> modsIterator = this.iterator();
		
		while (modsIterator.hasNext())
		{
			IModContainer currContainer = modsIterator.next();
			
			if (currContainer.getModId() == modId)
			{
				return currContainer;
			}
		}
		
		return null;
	}

	public int getModCount()
	{
		return mods.size();
	}
	
	/**
	 * Returns the mod register by it's id.
	 * @param modId
	 * @return
	 */
	public Object getModById(String modId)
	{
		return getModContainerById(modId).getMainClass();
	}
	
	/**
	 * Returns the underlying list of mod containers.
	 * @return
	 */
	public Map<String, IModContainer> getMods()
	{
		return mods;
	}
	
	/**
	 * Gets the corresponding container to the specified register.
	 * @param register
	 * @return
	 */
	public IModContainer getContainerFromRegister(Object register)
	{
		return containersToRegistersMap.get(register);
	}
	
	public boolean contains(IModContainer object)
	{
		for (Map.Entry<String, IModContainer> entry : this.mods.entrySet())
		{
			if (entry.getValue() == entry)
			{
				return true;
			}
		}

		return false;
	}

	public boolean doesModExist(String modid)
	{
		return this.contains(getModContainerById(modid)) || forgeModids.containsKey(modid);
	}

	/**
	 * Gets the iterator for this list.
	 * @return The iterator for this list.
	 */
	@Override
	public Iterator<IModContainer> iterator()
	{
		List<IModContainer> theMods = new ArrayList<>();

		for (Map.Entry<String, IModContainer> entry : this.mods.entrySet())
		{
			theMods.add(entry.getValue());
		}

		return theMods.iterator();
	}

	public boolean doesListContainLibrary(String modId)
	{
		Iterator<IModContainer> librariesIterator = this.iterator();

		while (librariesIterator.hasNext())
		{
			IModContainer currLib = librariesIterator.next();

			if (currLib.getModId() == modId && currLib.isLibrary())
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the library containers in this list.
	 * @return
	 */
	public List<IModContainer> getLibraryContainers()
	{
		List<IModContainer> libsToReturn = new ArrayList<>();
		Iterator<IModContainer> modsIter = this.iterator();

		while (modsIter.hasNext())
		{
			IModContainer nextMod = modsIter.next();

			if (nextMod.isLibrary())
			{
				libsToReturn.add(nextMod);
			}
		}

		return libsToReturn;
	}

	public List<Object> getLibraryRegisters()
	{
		List<IModContainer> libs = this.getLibraryContainers();
		List<Object> registersToReturn = new ArrayList<>();

		while (libs.iterator().hasNext())
		{
			registersToReturn.add(libs.iterator().next().getMainClass());
		}

		return registersToReturn;
	}

	public List<IModContainer> getModContainers()
	{
		List<IModContainer> modsToReturn = new ArrayList<>();
		Iterator<IModContainer> modsIter = this.iterator();

		while (modsIter.hasNext())
		{
			IModContainer nextMod = modsIter.next();

			if (!nextMod.isLibrary())
			{
				modsToReturn.add(nextMod);
			}
		}

		return modsToReturn;
	}

	public List<Object> getModRegisters()
	{
		List<IModContainer> libs = this.getModContainers();
		List<Object> registersToReturn = new ArrayList<>();

		while (libs.iterator().hasNext())
		{
			registersToReturn.add(libs.iterator().next().getMainClass());
		}

		return registersToReturn;
	}

	public String getIdForRegister(Object register)
	{
		for (String id : mods.keySet())
		{
			if (mods.get(id) == register)
			{
				return id;
			}
		}

		return "";
	}
}
