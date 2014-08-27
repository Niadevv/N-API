package co.uk.niadel.mpi.modhandler;

import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.mpi.modhandler.loadhandler.IModContainer;
import co.uk.niadel.mpi.modhandler.loadhandler.NModLoader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used for adding dependencies.
 */
public final class DependenciesRegistry
{
	/**
	 * The dependencies that have been registered. Key is the register, value is the list of modid dependencies for the key.
	 */
	public static Map<Object, List<String>> dependenciesMap = new HashMap<>();

	/**
	 * Library dependencies. The second map is keyed by library id, and valued by the minimum version of the library.
	 */
	public static Map<Object, List<Map<String, String>>> libDependenciesMap = new HashMap<>();

	/**
	 * Adds a single dependency.
	 * @param register The register that will have the dependency.
	 * @param dependency The dependency modid.
	 */
	public static final void addDependency(Object register, String dependency)
	{
		if (!dependenciesMap.containsKey(register))
		{
			dependenciesMap.put(register, Arrays.asList(dependency));
		}
		else
		{
			dependenciesMap.get(register).add(dependency);
		}
	}

	/**
	 * Adds a group of dependencies at once.
	 * @param register The register that will have the dependencies.
	 * @param dependencies The dependencies to register.
	 */
	public static final void addDependencies(Object register, String... dependencies)
	{
		for (String dependency : dependencies)
		{
			addDependency(register, dependency);
		}
	}

	/**
	 * Adds a library dependency.
	 * @param register The register that will have the dependency.
	 * @param libId The modid of the library dependency.
	 * @param minLibVersion The minimum version the library dependency has to be.
	 */
	public static final void addLibDependency(Object register, String libId, String minLibVersion)
	{
		if (!libDependenciesMap.containsKey(register))
		{
			Map<String, String> tempMap = new HashMap<>();
			tempMap.put(libId, minLibVersion);

			libDependenciesMap.put(register, Arrays.asList(tempMap));
		}
		else
		{
			HashMap<String, String> tempMap = new HashMap<>();
			tempMap.put(libId, minLibVersion);
			libDependenciesMap.get(register).add(tempMap);
		}
	}

	/**
	 * Gets whether or not the specified register has any dependencies.
	 * @param register The register to check.
	 * @return Whether or not the specified register has any dependencies.
	 */
	public static final boolean doesRegisterHaveDependencies(Object register)
	{
		return dependenciesMap.containsKey(register) || libDependenciesMap.containsKey(register);
	}

	/**
	 * Checks the dependencies of the specified register.
	 * @param registerToCheck The mod register to check the dependencies of.
	 * @return Whether or not the dependencies check was fully completed.
	 */
	@Internal
	public static final boolean checkDependencies(Object registerToCheck)
	{
		if (doesRegisterHaveDependencies(registerToCheck))
		{
			for (Map.Entry<Object, List<String>> dependencyEntry : dependenciesMap.entrySet())
			{
				for (String dependency : dependencyEntry.getValue())
				{
					if (NModLoader.doesModExist(dependency) || NModLoader.doesLibraryExist(dependency))
					{
						IModContainer dependencyContainer = NModLoader.getModContainerByModId(dependency);

						if (dependencyContainer.isLibrary())
						{
							if (NModLoader.mods.compareContainerVersions(NModLoader.getModContainerByModId(NModLoader.mods.getIdForRegister(registerToCheck)), dependencyContainer))
							{
								continue;
							}
							else
							{
								return false;
							}
						}
						else
						{
							continue;
						}
					}
					else
					{
						return false;
					}
				}
			}

			for (Map.Entry<Object, List<Map<String, String>>> libDepEntry : libDependenciesMap.entrySet())
			{
				for (Map<String, String> libDependencyEntry : libDepEntry.getValue())
				{
					for (Map.Entry<String, String> libDependencyEntry2 : libDependencyEntry.entrySet())
					{
						if (NModLoader.doesLibraryExist(libDependencyEntry2.getKey()))
						{
							if (NModLoader.mods.checkVersions(NModLoader.getModContainerByModId(NModLoader.mods.getIdForRegister(registerToCheck)), libDependencyEntry2.getValue()))
							{
								continue;
							}
							else
							{
								return false;
							}
						}
						else
						{
							return false;
						}
					}
				}
			}
		}

		return true;
	}
}
