package co.uk.niadel.mpi.modhandler.loadhandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import co.uk.niadel.mpi.modhandler.IModRegister;

public class Library extends Mod
{	
	/**
	 * A list of mod registers that this library depends on
	 */
	public List<IModRegister> dependencies = new ArrayList<>();
	
	public Library(String modId, String version, IModRegister mainClass)
	{
		super(modId, version, mainClass);
	}

	public Library(IModRegister mainClass)
	{
		super(mainClass);
	}
	
	public Library(String modId, String version, IModRegister mainClass, Annotation[] classAnnotations, Map<Method, Annotation[]> methodAnnotations)
	{
		super(modId, version, mainClass, classAnnotations, methodAnnotations);
	}
}
