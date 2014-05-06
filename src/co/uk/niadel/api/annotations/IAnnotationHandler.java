package co.uk.niadel.api.annotations;

import java.lang.annotation.Annotation;
import co.uk.niadel.api.modhandler.IModRegister;

public interface IAnnotationHandler
{
	/**
	 * Where to handle the register and class. This is quite a powerful feature for
	 * Library and MPI makers.
	 * @param annotation
	 * @param modRegister
	 */
	public void handleAnnotation(Annotation annotation, IModRegister modRegister);
}
