package co.uk.niadel.napi.util.reflection;

import co.uk.niadel.napi.util.NAPILogHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Allows one to call methods that are usually uncallable. Mainly here to remove all of the
 * boilerplate code that users of Reflection face. This is all actually Reflection code,
 * however.
 * 
 * @author Niadel
 *
 */
public final class ReflectionCallMethods
{
	/**
	 * Creates a class from a string and calls the method specified by methodName.
	 * @param className The name of the class you want to call the method of.
	 * @param methodName The name of the method to call.
	 * @param args The args of the method.
	 */
	public static final void callMethod(String className, String methodName, Object ... args)
	{
		try
		{
			Class<?> theClass = Class.forName(className);
			Method method = theClass.getDeclaredMethod(methodName, new Class[] {});
			method.setAccessible(true);
			method.invoke(theClass, args);
		}
		catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			NAPILogHelper.logError(e);
		}
	}
	
	/**
	 * Calls a method with a provided class.
	 * @param classToCallMethod The class that owns the method you want to call.
	 * @param methodName The name of the method to call.
	 * @param args The args of the method.
	 */
	public static final <X> void callMethod(Class<X> classToCallMethod, String methodName, Object ... args)
	{
		try
		{
			Method method = classToCallMethod.getDeclaredMethod(methodName, new Class[] {});
			method.setAccessible(true);
			method.invoke(classToCallMethod, args);
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			NAPILogHelper.logError(e);
		}
	}

	/**
	 * Calls the methods without any args.
	 * @param classToCallMethod The class that the method belongs to.
	 * @param methodName The name of the method to call.
	 * @param <X> The type classToCallMethod is of.
	 */
	public static final <X> void callArglessMethod(Class<X> classToCallMethod, String methodName)
	{
		callMethod(classToCallMethod, methodName, new Object[] {});
	}
}
