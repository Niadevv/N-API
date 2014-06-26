package co.uk.niadel.mpi.util;

import java.util.List;
import java.util.Random;
import co.uk.niadel.mpi.annotations.MPIAnnotations.Dangerous;

public class ListUtils
{
	/**
	 * Gets a random number not in the specified list and below the max number.
	 * @param listOfNumsToExclude
	 * @param maxNum
	 * @return
	 */
	@Dangerous(reason = "Potential infinite loop!")
	public static final int getNumberNotInList(List<Integer> listOfNumsToExclude, int maxNum)
	{
		Random rand = new Random();
		
		int testedId = rand.nextInt(maxNum);
		
		while (listOfNumsToExclude.contains(testedId))
		{
			testedId = rand.nextInt(maxNum);
		}
		
		return testedId;
	}
}
