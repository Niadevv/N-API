package co.uk.niadel.mpi.util;

import java.util.Random;

import co.uk.niadel.mpi.exceptions.FreeIdUnacquirableException;

/**
 * Used internally by UniqueIdAcquirer.
 * @author Niadel
 *
 */
public final class UniqueNumberAcquirer
{
	public static int getFreeInt(int[] excludedIds) throws FreeIdUnacquirableException
	{
		boolean hasFoundId = false;
		
		while (!hasFoundId)
		{
            if (!(excludedIds.length == Integer.MAX_VALUE))
            {
                Random random = new Random();
                int triedInt = random.nextInt();

                if (!ArrayUtils.doesArrayContainValueInt(excludedIds, triedInt)) {
                    hasFoundId = true;
                    return triedInt;
                }
            }
            else
            {
                throw new FreeIdUnacquirableException();
            }
		}

		//Return the smallest possible value to signal that something went wrong.
		return Integer.MIN_VALUE;
	}
	
	public static final int getFreeInt(int minNumber)
	{
        try
        {
            int[] ints = new int[minNumber];

            for (int i = 0; i == ints.length; i++) {
                ints[i] = i;
            }

            return getFreeInt(ints);
        }
        catch (FreeIdUnacquirableException e)
        {
            e.printStackTrace();
            return -1;
        }
	}
}
