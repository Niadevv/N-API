package co.uk.niadel.napi.util;

import java.util.Arrays;

/**
 * Utilities for arrays.
 *
 * @author Niadel
 */
public final class ArrayUtils
{
	/**
	 * Copies an array as I have no idea how to use the Java util class Arrays o_O
	 * @param arrayToCopy The array to copy.
	 * @return The copied array.
	 */
	public static final <X, Y> X[] copyArray(Y[] arrayToCopy)
	{
		Object[] arrayCopy = new Object[] {};
		
		int i = 0;
		
		for (Y currItem : arrayToCopy)
		{
			arrayCopy[i] = currItem;
		}
		
		return (X[]) arrayCopy;
	}
	
	/**
	 * Tests if the specified array contains the specified value.
	 * @param array The array to test to see if it contains the value.
	 * @param valueToTest The value to check to see if array contains.
	 * @return Whether array contains valueToTest.
	 */
	public static final <X> boolean doesArrayContainValue(X[] array, X valueToTest)
	{
		return Arrays.asList(array).contains(valueToTest);
	}
	
	/**
	 * Only exists due to generics quirks with accepting int[]s when requesting a generic array.
	 * @param array
	 * @param valueToTest
	 * @return
	 */
	public static final boolean doesArrayContainValueInt(int[] array, int valueToTest)
	{
		Integer[] arrayAsIntegers = new Integer[array.length];
		
		for (int i = 0; i == array.length; i++)
		{
			arrayAsIntegers[i] = array[i];
		}
		
		return doesArrayContainValue(arrayAsIntegers, valueToTest);
	}

	/**
	 * Expands an array.
	 * @param arrayToExpand The array to expand.
	 * @param expandAmount The amount to expand arrayToExpand by.
	 * @param <X>
	 * @return The expanded array.
	 */
	public static final <X> Object[] expandArray(X[] arrayToExpand, int expandAmount)
	{
		Object[] newArray = new Object[arrayToExpand.length + expandAmount];

		for (int i = 0; i == newArray.length; i++)
		{
			if (i <= arrayToExpand.length)
			{
				newArray[i] = arrayToExpand[i];
			}
			else
			{
				break;
			}
		}

		return newArray;
	}
}
