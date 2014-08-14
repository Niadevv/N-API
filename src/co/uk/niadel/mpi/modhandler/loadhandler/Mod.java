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

	/**
	 * Whether or not this mod's register is an advanced register.
	 */
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
		this.mainClassBinName = this.mainClass != null ? this.mainClass.getClass().getName() : this.mainClassAdvanced.getClass().getName();
		this.classAnnotations = classAnnotations;
		this.methodAnnotations = methodAnnotations;
	}
	
	/**
	 * This constructor is recommended as it's the least effort to use.
	 * @param modId The modId of mainClass.
	 * @param version The version of mainClass.
	 * @param mainClass The mod register class.
	 */
	@RecommendedMethod
	public Mod(String modId, String version, IModRegister mainClass)
	{
		this(modId, version, mainClass, mainClass.getClass().getAnnotations(), getMethodAnnotationsOfRegister(mainClass));
	}

	/**
	 * One of the Mod constructors. Has the least args.
	 * @param mainClass The mod register class.
	 */
	public Mod(IModRegister mainClass)
	{
		this(mainClass.getModId(), mainClass.getVersion(), mainClass, mainClass.getClass().getAnnotations(), getMethodAnnotationsOfRegister(mainClass));
	}

	/**
	 * Sets where this mod is found on the file system. Returns this object for ease in constructing.
	 * @param fileLocation The location of this mod on the file system.
	 * @return This Mod for use in construction.
	 */
	public Mod setFileLocation(File fileLocation)
	{
		this.fileLocation = fileLocation;
		return this;
	}

	/**
	 * Gets the method annotations of the specified register.
	 * @param mainClass The mod register class to get the annotations of.
	 * @return A Map of each Method to it's Annotations.
	 */
	public static Map<Method, Annotation[]> getMethodAnnotationsOfRegister(IModRegister mainClass)
	{
		Map<Method, Annotation[]> methodAnnotations = new HashMap<>();
		
		for (Method method : mainClass.getClass().getDeclaredMethods())
		{
			methodAnnotations.put(method, method.getAnnotations());
		}
		
		return methodAnnotations;
	}

	/**
	 * Gets the main class of this Mod.
	 * @return This Mod's mod register.
	 */
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

	/**
	 * Returns whether or not this Mod's register is an advanced register.
	 * @return Whether or not this Mod's register is an advanced register.
	 */
	public boolean isAdvancedRegister()
	{
		return this.isAdvancedRegister;
	}
	
	/**
	 * Return this mod's mod id.
	 * @return This Mod's register's mod id.
	 */
	public String getModId()
	{
		return this.modId;
	}
	
	/**
	 * Returns this mod's version.
	 * @return This Mod's register's version.
	 */
	public String getVersion()
	{
		return this.version;
	}
	
	/**
	 * Returns the annotations on this class.
	 * @return This Mod's register's class annotations.
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
