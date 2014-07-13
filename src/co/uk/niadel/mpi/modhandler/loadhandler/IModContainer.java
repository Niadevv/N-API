package co.uk.niadel.mpi.modhandler.loadhandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import co.uk.niadel.mpi.modhandler.IModRegister;

public interface IModContainer
{
	/**
	 * Should return the IModRegister that this container contains.
	 * @return
	 */
	public IModRegister getMainClass();
	
	/**
	 * Should get this container's mod's id.
	 * @return
	 */
	public String getModId();
	
	/**
	 * Should get this container's mod's version.
	 * @return
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
}
