package co.uk.niadel.napi.modhandler.nml;

import co.uk.niadel.commons.datamanagement.ValueExpandableMap;
import co.uk.niadel.napi.annotations.Internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used for handling dependencies.
 */
public final class DependenciesRegistry
{
	/**
	 * The dependencies that have been registered. Key is the register, value is the list of modid dependencies for the key.
	 *
	 */
	public static ValueExpandableMap<Object, String> dependenciesMap = new ValueExpandableMap<>();

	/**
	 * Library dependencies. The external map is keyed by the mod object, and valued by the second map.
	 * The second map is keyed by library id, and valued by the minimum version of the library.
	 *
	 */
	public static ValueExpandableMap<Object, Map<String, String>> libDependenciesMap = new ValueExpandableMap<>();

	/**
	 * Adds a single dependency.
	 * @param register The register that will have the dependency.
	 * @param dependency The dependency modid.
	 */
	public static final void addDependency(Object register, String dependency)
	{
		if (!dependenciesMap.containsKey(register))
		{
			dependenciesMap.put(register, dependency);
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
	public static final void addDependencies(Object register, List<String> dependencies)
	{
		for (String dependency : dependencies)
		{
			addDependency(register, dependency);
		}
	}

	public static final void addDependencies(Object register, String... dependencies)
	{
		addDependencies(register, Arrays.asList(dependencies));
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

			libDependenciesMap.put(register, tempMap);
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
		return dependenciesMap.containsKey(register) || doesRegisterHaveLibDependencies(register);
	}

	public static final boolean doesRegisterHaveLibDependencies(Object register)
	{
		return libDependenciesMap.containsKey(register);
	}

	/**
	 * Checks the dependencies of the specified register.
	 *
	 * @param registerToCheck The mod register to check the dependencies of.
	 * @return Whether or not the dependencies check was fully completed.
	 */
	@Internal
	public static final boolean checkDependencies(Object registerToCheck)
	{
		if (doesRegisterHaveDependencies(registerToCheck))
		{
			for (Object dependencyKey : dependenciesMap.keySet())
			{
				for (String dependency : dependenciesMap.get(dependencyKey))
				{
					if (!NModLoader.doesModExist(dependency))
					{
						return false;
					}
				}
			}

			if (doesRegisterHaveLibDependencies(registerToCheck))
			{
				for (Object dependencyKey : libDependenciesMap.keySet())
				{
					List<Map<String, String>> libDependencies = libDependenciesMap.get(dependencyKey);

					for (Map<String, String> libDependency : libDependencies)
					{
						for (String libDependencyName : libDependency.keySet())
						{
							String libDependencyMinVersion = libDependency.get(libDependencyName);

							if (!NModLoader.mods.checkVersions(NModLoader.getModContainerByModId(libDependencyName), libDependencyMinVersion))
							{
								return false;
							}
						}
					}
				}
			}
		}

		return true;
	}
}
