package co.uk.niadel.mpi.modhandler.loadhandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import co.uk.niadel.mpi.modhandler.IModRegister;

public class Library extends Mod implements IModContainer
{	
	/**
	 * A list of mod registers that this library depends on.
	 */
	public Set<IModRegister> dependencies = new HashSet<>();
	
	public Library(String modId, String version, IModRegister mainClass)
	{
		super(modId, version, mainClass);
	}

	public Library(IModRegister mainClass)
	{
		super(mainClass);

		if (this.isAdvancedRegister())
		{
			this.dependencies = mainClassAdvanced.dependencies;
		}
	}
	
	public Library(String modId, String version, IModRegister mainClass, Annotation[] classAnnotations, Map<Method, Annotation[]> methodAnnotations)
	{
		super(modId, version, mainClass, classAnnotations, methodAnnotations);

		if (this.isAdvancedRegister())
		{
			this.dependencies = mainClassAdvanced.dependencies;
		}
	}
	
	public Set<IModRegister> getDependencies()
	{
		return this.dependencies;
	}
	
	@Override
	public boolean isLibrary()
	{
		return true;
	}
}
