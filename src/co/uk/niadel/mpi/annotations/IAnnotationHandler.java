package co.uk.niadel.mpi.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * All annotation handlers must implement this. Annotation handlers can, when used correctly, be incredibly useful.
 * @author Niadel
 *
 */
public interface IAnnotationHandler
{
	/**
	 * Where to handle the register and class. This is quite a powerful feature for
	 * Library and MPI makers. However, it can lead to crashes, sometimes.
	 * 
	 * @param annotation The currently parsed annotation of modRegister.
	 * @param modRegister The mod register that is being parsed for annotations.
	 */
	public void handleAnnotation(Annotation annotation, Object modRegister);
	
	/**
	 * Handles methods with annotations.
	 * @param annotations The annotations of theMethod.
	 * @param theMethod The method currently being parsed for annotations in modRegister.
	 * @param modRegister The mod register that is being parsed for annotations.
	 */
	public void handleMethodAnnotations(Annotation[] annotations, Method theMethod, Object modRegister);
}
