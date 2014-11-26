package co.uk.niadel.napi.util.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Used for getting things that have annotations.
 * @author Niadel
 *
 */
public final class ReflectionGetWithAnnotation
{
	/**
	 * Gets an array of Method objects of a class that has the specified annotation.
	 * @param theClass The class to check the methods of.
	 * @param annotationToTestFor The annotation to test to see if the methods have.
	 * @return A list of methods that own annotationToTestFor.
	 */
	public static final <X> Method[] getMethodsWithAnnotation(Class<? extends X> theClass, Annotation annotationToTestFor)
	{
		Method[] methods = new Method[] {};
		
		for (Method method : theClass.getDeclaredMethods())
		{
			method.setAccessible(true);
			Annotation[] annotations = method.getDeclaredAnnotations();
			
			if (annotations.length != 0)
			{
				for (Annotation annotation : annotations)
				{
					if (annotation.annotationType() == annotationToTestFor.annotationType())
					{
						methods[methods.length - 1] = method;
					}
				}
			}
		}
		
		return methods;
	}
	
	/**
	 * Simple method that may be used in the future.
	 * @param theClass The class ot get the annotations of.
	 * @return The annotations on theClass.
	 */
	public static final <X> Annotation[] getClassAnnotations(Class<? extends X> theClass)
	{
		return theClass.getDeclaredAnnotations();
	}
}
