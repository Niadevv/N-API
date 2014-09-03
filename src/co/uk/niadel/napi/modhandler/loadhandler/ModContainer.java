package co.uk.niadel.napi.modhandler.loadhandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper around a mod object to allow you to get data about a mod, like modid and version.
 * Can be considered the "vanilla" mod container, but others are somewhat easy to implement.
 *
 * @author Niadel
 */
public class ModContainer implements IModContainer
{
	/**
	 * The mod object itself.
	 */
	public Object mod;

	/**
	 * The modid and the version of the mod in this container.
	 */
	public String modid, version;

	/**
	 * Whether or not this container is a library.
	 */
	public boolean isLibrary;

	public ModContainer(Object mod, String modid, String version, Object... otherParams)
	{
		this.mod = mod;
		this.modid = modid;
		this.version = version;
		this.isLibrary = (boolean) otherParams[0];
	}

	@Override
	public boolean isLibrary()
	{
		return this.isLibrary;
	}

	@Override
	public Map<Method, Annotation[]> getMethodAnnotations()
	{
		Map<Method, Annotation[]> methodAnnotations = new HashMap<>();

		for (Method method : getMainClass().getClass().getDeclaredMethods())
		{
			methodAnnotations.put(method, method.getAnnotations());
		}

		return methodAnnotations;
	}

	@Override
	public Annotation[] getClassAnnotations()
	{
		return getMainClass().getClass().getDeclaredAnnotations();
	}

	@Override
	public Object getMainClass()
	{
		return mod;
	}

	@Override
	public String getVersion()
	{
		return this.version;
	}

	@Override
	public String getModId()
	{
		return this.modid;
	}
}
