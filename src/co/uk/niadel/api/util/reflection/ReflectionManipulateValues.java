package co.uk.niadel.api.util.reflection;

import java.lang.reflect.*;

/**
 * I wish I'd made this earlier. So many base edits could have been avoided.
 * @author Niadel
 */
public final class ReflectionManipulateValues 
{
	
	/**
	 * If you want to set the value to a primitive type, use the wrapper class provided 
	 * in java.lang, eg. new Integer(1337);
	 * 
	 * NOTE: variableClassObject has to be created with new.
	 * @param objectClass
	 * @param variableName
	 * @param newValue
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public final static void setValue(Class<?> objectClass, Object variableClassObject, String variableName, Object newValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field value = objectClass.getDeclaredField(variableName);
		value.setAccessible(true);
		value.set(variableClassObject, newValue);
	}
	
	/**
	 * Returns the value. variableClassObject has to be created with new.
	 * @param objectClass
	 * @param variableClassObject
	 * @param variableName
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public final static Object getValue(Class<?> objectClass, Object variableClassObject, String variableName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field value = objectClass.getDeclaredField(variableName);
		value.setAccessible(true);
		Object variable = value.get(variableClassObject);
		return variable;
	}
}
