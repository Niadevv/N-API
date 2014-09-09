package co.uk.niadel.napi.client.resources;

import java.util.HashSet;
import java.util.Set;
import co.uk.niadel.commons.reflection.ReflectionManipulateValues;
import net.minecraft.client.resources.DefaultResourcePack;

/**
 * Where you add resource stuff, important for textures and sounds to work.
 * @author Niadel
 *
 */
public final class ResourcesRegistry
{
	/**
	 * Set that contains the domains that have been added.
	 */
	public static final Set<String> addedDomains = new HashSet<>();
	
	/**
	 * Adds a domain name. Call this in preInit, or else it will not work.
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
