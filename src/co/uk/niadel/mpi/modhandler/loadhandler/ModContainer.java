package co.uk.niadel.mpi.modhandler.loadhandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * The mod container used with @ModRegister.
 */
public class ModContainer implements IModContainer
{
	/**
	 * The mod object itself.
	 */
	public Object mod;

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

	public boolean isLibrary()
	{
		return this.isLibrary;
	}

	public Map<Method, Annotation[]> getMethodAnnotations()
	{
		Map<Method, Annotation[]> methodAnnotations = new HashMap<>();

		for (Method method : getMainClass().getClass().getDeclaredMethods())
		{
			methodAnnotations.put(method, method.getAnnotations());
		}

		return methodAnnotations;
	}

	public Annotation[] getClassAnnotations()
	{
		return getMainClass().getClass().getDeclaredAnnotations();
	}

	public Object getMainClass()
	{
		return mod;
	}

	public String getVersion()
	{
		return this.version;
	}

	public String getModId()
	{
		return this.modid;
	}
}
