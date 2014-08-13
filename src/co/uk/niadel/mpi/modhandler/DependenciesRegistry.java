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
public class DependenciesRegistry
{
	public static Map<IAdvancedModRegister, List<String>> dependenciesMap = new HashMap<>();

	/**
	 * Library dependencies. The second map is keyed by library id, and valued by the minimum version of the library.
	 */
	public static Map<IAdvancedModRegister, List<Map<String, String>>> libDependenciesMap = new HashMap<>();

	public static final void addDependency(IAdvancedModRegister register, String dependency)
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

	public static final void addDependencies(IAdvancedModRegister register, String... dependencies)
	{
		for (String dependency : dependencies)
		{
			addDependency(register, dependency);
		}
	}

	public static final void addLibDependency(IAdvancedModRegister register, String libId, String minLibVersion)
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

	public static final boolean doesRegisterHaveDependencies(IAdvancedModRegister register)
	{
		return dependenciesMap.containsKey(register) || libDependenciesMap.containsKey(register);
	}

	/**
	 * Checks the dependencies of the specified register.
	 * @param registerToCheck
	 * @return
	 */
	@Internal
	public static final boolean checkDependencies(IAdvancedModRegister registerToCheck)
	{
		if (doesRegisterHaveDependencies(registerToCheck))
		{
			for (Map.Entry<IAdvancedModRegister, List<String>> dependencyEntry : dependenciesMap.entrySet())
			{
				for (String dependency : dependencyEntry.getValue())
				{
					if (NModLoader.doesModExist(dependency) || NModLoader.doesLibraryExist(dependency))
					{
						IModContainer dependencyContainer = NModLoader.getModContainerByModId(dependency);

						if (dependencyContainer.isLibrary())
						{
							if (NModLoader.mods.compareContainerVersions(NModLoader.getModContainerByModId(registerToCheck.getModId()), dependencyContainer))
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

			for (Map.Entry<IAdvancedModRegister, List<Map<String, String>>> libDepEntry : libDependenciesMap.entrySet())
			{
				for (Map<String, String> libDependencyEntry : libDepEntry.getValue())
				{
					for (Map.Entry<String, String> libDependencyEntry2 : libDependencyEntry.entrySet())
					{
						if (NModLoader.doesLibraryExist(libDependencyEntry2.getKey()))
						{
							if (NModLoader.mods.checkVersions(NModLoader.getModContainerByModId(libDepEntry.getKey().getModId()), libDependencyEntry2.getValue()))
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
