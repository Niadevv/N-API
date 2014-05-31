package co.uk.niadel.mpi.annotations;

import java.util.HashSet;
import java.util.Set;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Internal;

/**
 * Where you add annotations that are used by N-API MPIs or libraries to add Annotation
 * functionality.
 * @author Niadel
 *
 */
public class AnnotationHandlerRegistry
{
	/**
	 * A Set that contains the Annotation Handlers.
	 */
	public static Set<IAnnotationHandler> annotationHandlers = new HashSet<>();
	
	/**
	 * Adds an annotation handler to annotationHandlers.
	 * @param handler
	 */
	public static final void addAnnotationHandler(IAnnotationHandler handler)
	{
		annotationHandlers.add(handler);
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
