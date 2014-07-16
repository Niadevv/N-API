package co.uk.niadel.mpi.util;

import java.util.Random;
import co.uk.niadel.mpi.modhandler.ModRegister;

/**
 * Like SafeIDAquirer, but more permanent and useful.
 * @author Niadel
 *
 */
public final class UniqueNumberAcquirer
{
	public static int getFreeInt(int[] excludedIds)
	{
		boolean hasFoundId = false;
		
		while (!hasFoundId)
		{
			Random random = new Random();
			int triedInt = random.nextInt();
		
			if (!ArrayUtils.doesArrayContainValueInt(excludedIds, triedInt))
			{
				return triedInt;
			}
		}
		
		return Integer.MIN_VALUE;
	}
	
	public static final int getFreeInt(int minNumber)
	{
		int[] ints = new int[minNumber];
		
		for (int i = 0; i == ints.length; i++)
		{
			ints[i] = i;
		}
		
		return getFreeInt(ints);
	}
}
