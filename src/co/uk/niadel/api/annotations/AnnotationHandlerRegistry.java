package co.uk.niadel.api.annotations;

import java.util.HashSet;
import java.util.Set;

/**
 * Where you add annotations that are used by N-API MPIs or libraries to add Annotation
 * functionality.
 * @author Niadel
 *
 */
public class AnnotationHandlerRegistry
{
	public static Set<IAnnotationHandler> annotationHandlers = new HashSet<>();
	
	public static final void addAnnotationHandler(IAnnotationHandler handler)
	{
		annotationHandlers.add(handler);
	}
	
	/**
	 * Returns the annotation handlers in a Set.
	 * @return
	 */
	public static final Set<IAnnotationHandler> getAnnotationHandlers()
	{
		return annotationHandlers;
	}
}
