package co.uk.niadel.mpi.modhandler.loadhandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import co.uk.niadel.mpi.modhandler.IModRegister;
import co.uk.niadel.mpi.annotations.MPIAnnotations.RecommendedMethod;

/**
 * What is handled in loading by NModloader to make code a bit smaller. I guess it's a bit
 * like FML's ModContainer. This is somewhat flexible so as to support other loaders if other
 * mods dislike my own loader or need to add something to it.
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
	
	public Mod(String modId, String version, IModRegister mainClass, Annotation[] classAnnotations, Map<Method, Annotation[]> methodAnnotations)
	{
		this.modId = modId;
		this.version = version;
		this.mainClass = mainClass;
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
		this.modId = modId;
		this.version = version;
		this.mainClass = mainClass;
		this.mainClass.getClass().getName();
		this.classAnnotations = mainClass.getClass().getAnnotations();
		this.methodAnnotations = getMethodAnnotationsOfRegister(mainClass);
	}
	
	public Mod(IModRegister mainClass)
	{
		this(mainClass.getModId(), mainClass.getVersion(), mainClass, mainClass.getClass().getAnnotations(), getMethodAnnotationsOfRegister(mainClass));
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
		return this.mainClass;
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
}
