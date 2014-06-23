package co.uk.niadel.mpi.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import co.uk.niadel.mpi.modhandler.IModRegister;

/**
 * All annotation handlers must implement this.
 * @author Niadel
 *
 */
public interface IAnnotationHandler
{
	/**
	 * Where to handle the register and class. This is quite a powerful feature for
	 * Library and MPI makers. However, it can lead to crashes, sometimes.
	 * @param annotation
	 * @param modRegister
	 */
	public void handleAnnotation(Annotation annotation, IModRegister modRegister);
	
	/**
	 * Handles methods with annotations.
	 * @param annotation
	 * @param theMethod
	 * @param modRegister
	 */
	public void handleMethodAnnotations(Annotation[] annotations, Method theMethod, IModRegister modRegister);
}
