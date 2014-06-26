package co.uk.niadel.mpi.util;

import java.util.Arrays;

public class ArrayUtils
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
		if (Arrays.asList(array).contains(valueToTest))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
