package co.uk.niadel.api.util;

import java.util.Random;

/**
 * Like SafeIDAquirer, but more permanent and useful.
 * @author Niadel
 *
 */
public class UniqueNumberAcquirer
{
	public static int getFreeInt(Integer[] numbersToExclude)
	{
		boolean hasFoundId = false;
		
		while (!hasFoundId)
		{
			Random random = new Random();
			int triedInt = random.nextInt();
		
			if (!UtilityMethods.<Integer>doesArrayContainValue(numbersToExclude, triedInt))
			{
				return triedInt;
			}
		}
		
		return Integer.MIN_VALUE;
	}
}
