package co.uk.niadel.api.util.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Used for getting things that have annotations.
 * @author Daniel1
 *
 */
public class ReflectionGetWithAnnotation
{
	/**
	 * Gets an array of Method objects of a class that has the specified annotation.
	 * @param theClass
	 * @param annotationToTestFor
	 * @return
	 */
	public <X> Method[] getMethodWithAnnotation(Class<? extends X> theClass, Annotation annotationToTestFor)
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
	 * @param theClass
	 * @return
	 */
	public <X> Annotation[] getClassAnnotations(Class<? extends X> theClass)
	{
		return theClass.getDeclaredAnnotations();
	}
}
