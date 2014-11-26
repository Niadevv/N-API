package co.uk.niadel.napi.util.reflection;

import co.uk.niadel.napi.util.NAPILogHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Reflection utilities for getting and setting values.
 * @author Niadel
 */
public final class ReflectionManipulateValues 
{
	/**
	 * If you want to set the value to a primitive type, use the wrapper class provided 
	 * in java.lang, eg. new Integer(1337); Pass null for setting static fields.
	 *
	 * @param objectClass The class that owns the intended field.
	 * @param variableClassObject The object of the type that objectClass is.
	 * @param variableName The name of the variable to set the value of.
	 * @param newValue The new value to set variableName to.
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
			NAPILogHelper.logError(e);
		}
	}
	
	/**
	 * The simpler version of the above method. However, it assumes the value is non-static.
	 * @param objectClass The class owning the field.
	 * @param variableName The name of the variable to set the value of.
	 * @param newValue The new value to set variableName to.
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
			NAPILogHelper.logError(e);
		}
	}
	
	/**
	 * Bypass for static final fields, which can't be set by regular Reflection methods.
	 * @param objectClass The class that owns the intended value to be set.
	 * @param varName The name of the variable containing this value.
	 * @param newValue The new value to assign to the value.
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
			NAPILogHelper.logError(e);
		}
	}
	
	/**
	 * Returns the value. variableClassObject has to be created with new.
	 * @param objectClass The class owning the intended field.
	 * @param variableClassObject The object of the type that objectClass is.
	 * @param variableName The name of the variable to get the value of.
	 * @return The value specified by objectClass, variableClassObject, and variableName.
	 */
	public static final <X, Y> Y getValue(Class<? extends X> objectClass, X variableClassObject, String variableName)
	{
		try
		{
			Field value = objectClass.getDeclaredField(variableName);
			value.setAccessible(true);
			return (Y) value.get(variableClassObject);
		}
		catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e)
		{
			NAPILogHelper.logError(e);
		}
		
		throw new RuntimeException("There was an error getting the variable " + variableName + " in " + objectClass.getName());
	}
}
