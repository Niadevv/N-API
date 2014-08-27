package co.uk.niadel.mpi.modhandler.loadhandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import co.uk.niadel.mpi.annotations.IAnnotationHandler;
import co.uk.niadel.mpi.annotations.MPIAnnotations.*;
import co.uk.niadel.mpi.annotations.UnstableMod;
import co.uk.niadel.mpi.modhandler.IModRegister;
import co.uk.niadel.mpi.util.NAPILogHelper;

/**
 * The annotation handler used by N-API.
 */
public class AnnotationHandlerNAPI implements IAnnotationHandler
{
	@Override
	public void handleAnnotation(Annotation annotation, IModRegister modRegister)
	{
		boolean shouldLoadAsLibrary = false;
		
		//If the class has the @Library annotation, add it to the modLibraries list.
		if (annotation.annotationType().getClass().equals(Library.class))
		{
			//Add it to the libraries list instead of the mods list.
			shouldLoadAsLibrary = true;
		}
		else if (annotation.annotationType() == UnstableLibrary.class)
		{
			//Tell the user that the library is unstable and mods using it could break
			NAPILogHelper.log("[IMPORTANT] " + ((UnstableLibrary) annotation).specialMessage());
			shouldLoadAsLibrary = true;
		}
		//Old @ModRegister code.
		/*else if (annotation.annotationType() == ModRegister.class)
		{
			//Set values of the register with Reflection because for some reason interface values are final by default.
			//Huh, you learn something new every day. Unless you're dead :3
			ReflectionManipulateValues.setValue(annotation.getClass(), "VERSION", ((ModRegister) annotation).version());
			ReflectionManipulateValues.setValue(annotation.getClass(), "MODID", ((ModRegister) annotation).modId());
			ReflectionManipulateValues.setValue(annotation.getClass(), "isUsingAnnotation", true);
			shouldLoadAsLibrary = false;
		}*/

		/*if (shouldLoadAsLibrary)
		{
			NModLoader.loadLibrary(modRegister);
		}
		else
		{
			NModLoader.loadMod(new ModContainer(modRegister));
		}*/
	}

	@Override
	public void handleMethodAnnotations(Annotation[] annotations, Method theMethod, IModRegister modRegister)
	{
		for (Annotation annotation : annotations)
		{

		}
	}
}
