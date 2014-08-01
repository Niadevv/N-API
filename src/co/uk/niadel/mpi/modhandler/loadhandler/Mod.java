package co.uk.niadel.mpi.modhandler.loadhandler;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import co.uk.niadel.mpi.modhandler.IAdvancedModRegister;
import co.uk.niadel.mpi.modhandler.IModRegister;
import co.uk.niadel.mpi.annotations.MPIAnnotations.RecommendedMethod;

/**
 * What is handled in loading by NModloader to make code a bit smaller. I guess it's a bit
 * like FML's ModContainer. This is somewhat flexible so as to support other loaders if other
 * mods dislike my own loader or need to add something to it. The main use is to collect all of
 * a mod's data into a single object for easier loading and ease of updating without killing as many mods.
 * @author Niadel
 *
 */
public class Mod implements IModContainer
{
	/**
	 * The mod's modId.
	 */
	public String modId;
	
	/**
	 * This mod's version.
	 */
	public String version;
	
	/**
	 * The class that has the preModInit classes etc.
	 */
	public IModRegister mainClass;

	/**
	 * The Advanced mod register, if it's given one.
	 */
	public IAdvancedModRegister mainClassAdvanced;
	
	/**
	 * The fully qualified (or binary) name of mainClass.
	 */
	public String mainClassBinName;
	
	/**
	 * An array of annotations that are labeled on the class itself.
	 */
	public Annotation[] classAnnotations;
	
	/**
	 * Map containing all of the mod's methods in the mod register
	 */
	public Map<Method, Annotation[]> methodAnnotations = new HashMap<>();

	public boolean isAdvancedRegister;

	/**
	 * The file this mod is on the file system.
	 */
	public File fileLocation;
	
	public Mod(String modId, String version, Object mainClass, Annotation[] classAnnotations, Map<Method, Annotation[]> methodAnnotations)
	{
		if (mainClass instanceof IAdvancedModRegister)
		{
			this.mainClassAdvanced = (IAdvancedModRegister) mainClass;
			this.isAdvancedRegister = true;
		}
		else if (mainClass instanceof IModRegister)
		{
			this.mainClass = (IModRegister) mainClass;
			this.isAdvancedRegister = false;
		}
		else
		{
			throw new IllegalArgumentException("Somehow got a non IModRegister passed! Aborting load!");
		}

		this.modId = modId;
		this.version = version;
		this.mainClassBinName = this.mainClass.getClass().getName();
		this.classAnnotations = classAnnotations;
		this.methodAnnotations = methodAnnotations;
	}
	
	/**
	 * This constructor is recommended as it's the least effort to use.
	 * @param modId
	 * @param version
	 * @param mainClass
	 */
	@RecommendedMethod
	public Mod(String modId, String version, IModRegister mainClass)
	{
		this(modId, version, mainClass, mainClass.getClass().getAnnotations(), getMethodAnnotationsOfRegister(mainClass));
	}
	
	public Mod(IModRegister mainClass)
	{
		this(mainClass.getModId(), mainClass.getVersion(), mainClass, mainClass.getClass().getAnnotations(), getMethodAnnotationsOfRegister(mainClass));
	}

	/**
	 * Sets where this mod is found on the file system. Returns this object for ease in constructing.
	 * @param fileLocation
	 * @return
	 */
	public Mod setFileLocation(File fileLocation)
	{
		this.fileLocation = fileLocation;
		return this;
	}
	
	public static Map<Method, Annotation[]> getMethodAnnotationsOfRegister(IModRegister mainClass)
	{
		Map<Method, Annotation[]> methodAnnotations = new HashMap<>();
		
		for (Method method : mainClass.getClass().getDeclaredMethods())
		{
			methodAnnotations.put(method, method.getAnnotations());
		}
		
		return methodAnnotations;
	}
	
	public IModRegister getMainClass()
	{
		if (this.isAdvancedRegister)
		{
			return this.mainClassAdvanced;
		}
		else
		{
			return this.mainClass;
		}
	}

	public boolean isAdvancedRegister()
	{
		return this.isAdvancedRegister;
	}
	
	/**
	 * Return this mod's mod id.
	 * @return
	 */
	public String getModId()
	{
		return this.modId;
	}
	
	/**
	 * Returns this mod's version.
	 * @return
	 */
	public String getVersion()
	{
		return this.version;
	}
	
	/**
	 * Returns the annotations on this class.
	 * @return
	 */
	public Annotation[] getClassAnnotations()
	{
		return this.classAnnotations;
	}
	
	public Map<Method, Annotation[]> getMethodAnnotations()
	{
		return this.methodAnnotations;
	}
	
	public void invokePreInit()
	{
		this.mainClass.preModInit();
	}
	
	public void invokeInit()
	{
		this.mainClass.modInit();
	}
	
	public void invokePostInit()
	{
		this.mainClass.postModInit();
	}

	public void registerTransformers()
	{
		if (this.isAdvancedRegister)
		{
			this.mainClassAdvanced.registerTransformers();
		}
	}
	
	public boolean isLibrary()
	{
		return false;
	}
}
