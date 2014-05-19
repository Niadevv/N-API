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
	 */
	public final static <X> void setValue(Class<? extends X> objectClass, Object variableClassObject, String variableName, Object newValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		try 
		{
			Field value = objectClass.getDeclaredField(variableName);
			value.setAccessible(true);
			value.set(variableClassObject, newValue);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * The simpler version of the above method.
	 * @param objectClass
	 * @param variableName
	 * @param newValue
	 */
	public final static <X> void setValue(Class<? extends X> objectClass, String variableName, Object newValue)
	{
		try
		{
			Field value = objectClass.getDeclaredField(variableName);
			value.setAccessible(true);
			value.set(objectClass.newInstance(), newValue);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the value. variableClassObject has to be created with new.
	 * @param objectClass
	 * @param variableClassObject
	 * @param variableName
	 * @return
	 */
	public final static <X, Y> Y getValue(Class<? extends X> objectClass, Object variableClassObject, String variableName)
	{
		Y variable = null;
		
		try
		{
			Field value = objectClass.getDeclaredField(variableName);
			value.setAccessible(true);
			variable = (Y) value.get(variableClassObject);
		}
		catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		
		return variable;
	}
}
