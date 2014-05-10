package co.uk.niadel.api.util.reflection;

import java.lang.reflect.*;


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
	public final static void callMethod(String className, String methodName, Object ... args) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException 
	{
		Class<?> theClass = Class.forName(className);
		Method method = theClass.getDeclaredMethod("methodName", new Class[] {});
		method.setAccessible(true);
		method.invoke(theClass, args);
	}
	
	public final static void callMethod(Class<?> classToCallMethod, String methodName, Object ... args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Method method = classToCallMethod.getDeclaredMethod(methodName, new Class[] {});
		method.setAccessible(true);
		method.invoke(classToCallMethod, args);
	}
}
