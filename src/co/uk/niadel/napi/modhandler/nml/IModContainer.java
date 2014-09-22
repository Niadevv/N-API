package co.uk.niadel.napi.modhandler.nml;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Implemented by all mod containers. Mod containers basically wrap around mod objects and allow ease of acquisition of
 * data about the container.
 *
 * @author Niadel
 */
public interface IModContainer
{
	/**
	 * Should return the IModRegister that this container contains.
	 * @return
	 */
	public Object getMainClass();
	
	/**
	 * Should get this container's mod's id.
	 * @return This container's mod id.
	 */
	public String getModId();
	
	/**
	 * Should get this container's mod's version.
	 * @return This container's version.
	 */
	public String getVersion();
	
	/**
	 * Should get this container's mod's annotations.
	 * @return
	 */
	public Annotation[] getClassAnnotations();
	
	/**
	 * Should return this container's mod's methods and their annotations.
	 * @return
	 */
	public Map<Method, Annotation[]> getMethodAnnotations();

	public boolean isLibrary();
}
