package co.uk.niadel.napi.util.reflection;

import java.lang.reflect.*;

/**
 * I wish I'd made this earlier. So many base edits could have been avoided.
 * @author Niadel
 */
public final class ReflectionManipulateValues 
{
	/**
	 * If you want to set the value to a primitive type, use the wrapper class provided 
	 * in java.lang, eg. new Integer(1337); Pass null for setting static fields.
	 * 
	 * NOTE: variableClassObject has to be created with new.
	 * @param objectClass
	 * @param variableName
	 * @param newValue 
	 */
	public final static <X> void setValue(Class<? extends X> objectClass, Object variableClassObject, String variableName, Object newValue)
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
	 * The simpler version of the above method. However, it assumes the value is non-static.
	 * @param objectClass
	 * @param newValue
	 */
	public static final <X> void setValue(Class<? extends X> objectClass, String variableName, Object newValue)
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
	 * Bypass for static final fields, which can't be set by regular Reflection methods. Very "cheaty Nebris".
	 * @param objectClass
	 * @param newValue
	 * @param varName
	 */
	public static final <X, Y> void setSFValue(Class<? extends X> objectClass, String varName, Y newValue)
	{
		try
		{
			Field value = objectClass.getDeclaredField(varName);
			value.setAccessible(true);
			
			Field modifierField = Field.class.getDeclaredField("modifiers");
			modifierField.setAccessible(true);
			
			//Borrowed cheaty code to bypass static finals.
			modifierField.setInt(value, value.getModifiers() & ~Modifier.FINAL);
			value.set(null, newValue);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
		{
			//TEMP
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
	public static final <X, Y> Y getValue(Class<? extends X> objectClass, Object variableClassObject, String variableName)
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
