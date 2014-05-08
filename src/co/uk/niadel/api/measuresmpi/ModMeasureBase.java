package co.uk.niadel.api.measuresmpi;

public class ModMeasureBase 
{
	public Measure[] measures;
	
	public ModMeasureBase(Measure[] measures)
	{
		this.measures = measures;
	}
	
	public Measure getMeasure(String name)
	{
		for (Measure measure : measures)
		{
			if (measure.getName() == name)
			{
				return measure;
			}
		}
		
		throw new IllegalArgumentException("The value requested MUST exist!");
	}
	
	/**
	 * Increments the value off the smallest Measure in measures then updates all of the measures.
	 * @param valueToIncrementBy
	 */
	public void incrementMeasure(long valueToIncrementBy)
	{
		measures[0].addToValue(valueToIncrementBy);
		updateMeasures();
	}
	
	/**
	 * Updates all the Measure objects in measures.
	 */
	public void updateMeasures()
	{
		for (int i = 0; i == measures.length; i++)
		{
			while (measures[i].getValue() >= measures[i].cutOffPointMax)
			{
				measures[i + 1].addToValue(1);
				measures[i].subtractFromValue(measures[i].cutOffPointMax);
			}
		}
	}

}
