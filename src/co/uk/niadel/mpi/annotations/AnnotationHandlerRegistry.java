package co.uk.niadel.mpi.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;
import co.uk.niadel.mpi.modhandler.IModRegister;

/**
 * Where you add annotations that are used by N-API MPIs or libraries to add Annotation
 * functionality.
 * @author Niadel
 *
 */
public final class AnnotationHandlerRegistry
{
	/**
	 * A Set that contains the Annotation Handlers.
	 */
	public static Set<IAnnotationHandler> annotationHandlers = new HashSet<>();
	
	/**
	 * Handlers that handle method annotations in Mod Registers.
	 */
	public static Set<IAnnotationHandler> methodAnnotationHandlers = new HashSet<>();
	
	/**
	 * Adds an annotation handler to annotationHandlers.
	 * @param handler
	 */
	public static final void addAnnotationHandler(IAnnotationHandler handler)
	{
		annotationHandlers.add(handler);
	}
	
	/**
	 * Adds a handler that handles method annotations in Mod Registers.
	 * @param handler
	 */
	public static final void addMethodAnnotationHandler(IAnnotationHandler handler)
	{
		methodAnnotationHandlers.add(handler);
	}
	
	/**
	 * Calls all method annotation handlers.
	 * @param annotations
	 * @param method
	 * @param register
	 */
	@Internal
	public static final void callAllMethodHandlers(Annotation[] annotations, Method method, IModRegister register)
	{
		for (IAnnotationHandler handler : methodAnnotationHandlers)
		{
			handler.handleMethodAnnotations(annotations, method, register);
		}
	}
	
	/**
	 * Returns the annotation handlers in a Set.
	 * @return
	 */
	@Internal
	public static final Set<IAnnotationHandler> getAnnotationHandlers()
	{
		return annotationHandlers;
	}
}
