package co.uk.niadel.mpi.measuresmpi;

import net.minecraft.block.Block;

/**
 * The base for groups of measures.
 * @author Niadel
 *
 */
public abstract class ModMeasureBase 
{
	/**
	 * This groups array of Measure objects.
	 */
	protected Measure[] measures;

	/**
	 * The Block representation of this, if it has any.
	 */
	public Block associatedBlock;
	
	public ModMeasureBase(Measure[] measures)
	{
		this.measures = measures;
	}
	
	/**
	 * Sets the specified measure to the specified value.
	 * @param name The Measure inside this measure's name to set.
	 * @param newValue The new value of the Measure in this measure with the name name.
	 */
	public void setMeasure(String name, long newValue)
	{
		this.getMeasure(name).setValue(newValue);
	}
	
	/**
	 * Returns the requested measure.
	 * @param name The name of the measure to get.
	 * @return The requested measure.
	 */
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
	 * Returns this objects Measure array.
	 * @return this.measures
	 */
	public Measure[] getMeasures()
	{
		return this.measures;
	}
	
	/**
	 * Sets this measure's Measure array.
	 * @param newMeasures The new Measures of this measure.
	 */
	public void setMeasures(Measure[] newMeasures)
	{
		this.measures = newMeasures;
	}

	/**
	 * Sets the associated block of this measure, if any.
	 * @param block The new associated block of this measure.
	 */
	public void setAssociatedBlock(Block block)
	{
		this.associatedBlock = block;
	}
	
	/**
	 * Increments the value of the smallest Measure in measures then updates all of the measures.
	 * @param valueToIncrementBy The value to increment by.
	 */
	public void incrementMeasure(long valueToIncrementBy)
	{
		measures[0].addToValue(valueToIncrementBy);
		updateMeasures();
	}
	
	/**
	 * Subtracts the value of the smallest Measure in measures then updates all of the Measure objects.
	 * @param valueToDecrementBy The value to decrement by.
	 */
	public void decrementMeasure(long valueToDecrementBy)
	{
		measures[0].subtractFromValue(valueToDecrementBy);
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
			
			if (measures[i].getValue() <= 0)
			{
				measures[i - 1].setValue(measures[i - 1].cutOffPointMax - 1);
				measures[i].setValue(0);
			}
		}
	}

	public boolean isEnergyProducer()
	{
		return false;
	}

	public abstract boolean isLiquidMeasure();
}
