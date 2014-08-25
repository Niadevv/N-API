package co.uk.niadel.mpi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import co.uk.niadel.mpi.modhandler.IModRegister;
import co.uk.niadel.mpi.modhandler.loadhandler.IModContainer;
import co.uk.niadel.mpi.modhandler.loadhandler.Library;
import co.uk.niadel.mpi.modhandler.loadhandler.Mod;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;

/**
 * A wrapper list around a list of mod containers (either Mod or Library at time of writing) for convenience.
 * @author Niadel
 *
 */
public class ModList implements Iterable<IModContainer>
{
	/**
	 * The list the mods are stored in.
	 */
	public List<IModContainer> mods = new ArrayList<>();

	/**
	 * Modids and versions that belong to Forge.
	 */
	public Map<String, String> forgeModids = NModLoader.forgeModids;
	
	/**
	 * Used in getContainerFromRegister.
	 */
	public DoubleMap<IModRegister, IModContainer> containersToRegistersMap = new DoubleMap<>();
	
	/**
	 * Adds a mod container.
	 * @param mod
	 */
	public void addMod(IModContainer mod)
	{
		this.mods.add(mod);
	}
	
	/**
	 * Converts the specified mod register into either a Mod object or a Library object depending on
	 * isLibrary.
	 * @param mod
	 * @param isLibrary
	 */
	public void addMod(IModContainer mod, boolean isLibrary)
	{
		if (!isLibrary)
		{
			this.mods.add(mod);
		}
		else
		{
			this.mods.add(mod);
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
		Iterator<IModContainer> modsIterator = this.mods.iterator();
		
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
	public List<IModContainer> getMods()
	{
		return mods;
	}
	
	/**
	 * Gets the corresponding container to the specified register.
	 * @param register
	 * @return
	 */
	public IModContainer getContainerFromRegister(IModRegister register)
	{
		return containersToRegistersMap.get(register);
	}
	
	public boolean contains(IModContainer object)
	{
		return mods.contains(object);
	}

	public boolean doesModExist(String modid)
	{
		return mods.contains(getModContainerById(modid)) || forgeModids.containsKey(modid);
	}

	/**
	 * Gets the iterator for this list.
	 * @return The iterator for this list.
	 */
	@Override
	public Iterator<IModContainer> iterator()
	{
		return mods.iterator();
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
	public List<Library> getLibraryContainers()
	{
		List<Library> libsToReturn = new ArrayList<>();
		Iterator<IModContainer> modsIter = this.iterator();

		while (modsIter.hasNext())
		{
			IModContainer nextMod = modsIter.next();

			if (nextMod.isLibrary())
			{
				libsToReturn.add((Library) nextMod);
			}
		}

		return libsToReturn;
	}

	public List<IModRegister> getLibraryRegisters()
	{
		List<Library> libs = this.getLibraryContainers();
		List<IModRegister> registersToReturn = new ArrayList<>();

		while (libs.iterator().hasNext())
		{
			registersToReturn.add(libs.iterator().next().getMainClass());
		}

		return registersToReturn;
	}

	public List<Mod> getModContainers()
	{
		List<Mod> modsToReturn = new ArrayList<>();
		Iterator<IModContainer> modsIter = this.iterator();

		while (modsIter.hasNext())
		{
			IModContainer nextMod = modsIter.next();

			if (!nextMod.isLibrary())
			{
				modsToReturn.add((Mod) nextMod);
			}
		}

		return modsToReturn;
	}

	public List<IModRegister> getModRegisters()
	{
		List<Mod> libs = this.getModContainers();
		List<IModRegister> registersToReturn = new ArrayList<>();

		while (libs.iterator().hasNext())
		{
			registersToReturn.add(libs.iterator().next().getMainClass());
		}

		return registersToReturn;
	}
}
