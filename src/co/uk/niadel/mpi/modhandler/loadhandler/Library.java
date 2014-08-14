package co.uk.niadel.mpi.modhandler.loadhandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import co.uk.niadel.mpi.modhandler.DependenciesRegistry;
import co.uk.niadel.mpi.modhandler.IModRegister;

public class Library extends Mod implements IModContainer
{	
	/**
	 * A list of mod registers that this library depends on.
	 */
	public List<String> dependencies = new ArrayList<>();
	
	public Library(String modId, String version, IModRegister mainClass)
	{
		super(modId, version, mainClass);
	}

	public Library(IModRegister mainClass)
	{
		super(mainClass);

		if (this.isAdvancedRegister())
		{
			this.dependencies = DependenciesRegistry.dependenciesMap.get(this.mainClassAdvanced);
		}
	}
	
	public Library(String modId, String version, IModRegister mainClass, Annotation[] classAnnotations, Map<Method, Annotation[]> methodAnnotations)
	{
		super(modId, version, mainClass, classAnnotations, methodAnnotations);

		if (this.isAdvancedRegister())
		{
			this.dependencies = DependenciesRegistry.dependenciesMap.get(this.mainClassAdvanced);
		}
	}
	
	public List<String> getDependencies()
	{
		return this.dependencies;
	}
	
	@Override
	public boolean isLibrary()
	{
		return true;
	}
}
