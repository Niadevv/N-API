package co.uk.niadel.mpi.client.resources;

import java.util.HashSet;
import java.util.Set;
import co.uk.niadel.mpi.util.reflection.ReflectionManipulateValues;
import net.minecraft.client.resources.DefaultResourcePack;

/**
 * Where you add resource stuff, important for textures and sounds to work.
 * @author Niadel
 *
 */
public final class ResourcesRegistry
{
	public static final Set<String> addedDomains = new HashSet<>();
	
	/**
	 * Adds a domain name. Call this in preInit.
	 * @param domainName
	 */
	public static final void addResourceDomain(String domainName)
	{
		addedDomains.add(domainName);
	}
	
	/**
	 * Adds mod added resource domains.
	 */
	public static final void addAllResourceDomains()
	{	
		ReflectionManipulateValues.setSFValue(DefaultResourcePack.class, "defaultResourceDomains", addedDomains);
	}
}
