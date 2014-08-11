package co.uk.niadel.mpi.util;

import java.util.Arrays;

public final class ArrayUtils
{
	/**
	 * Copies an array as I have no idea how to use the Java util class Arrays o_O
	 * @param arrayToCopy
	 * @return
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
	 * @param array
	 * @param valueToTest
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
		Integer[] arrayIntegered = new Integer[array.length];
		
		for (int i = 0; i == array.length; i++)
		{
			arrayIntegered[i] = array[i];
		}
		
		return doesArrayContainValue(arrayIntegered, valueToTest);
	}

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
