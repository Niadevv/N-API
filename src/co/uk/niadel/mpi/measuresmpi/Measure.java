package co.uk.niadel.mpi.measuresmpi;

/**
 * The actual Measure itself. The ModMeasure classes handle these internally.
 * @author Niadel
 *
 */
public final class Measure
{
	/**
	 * The value of this measure.
	 */
	private long value;
	
	/**
	 * This value's name.
	 */
	private String name;
	
	/**
	 * The maximum value this can be before the next highest Measure is incremented.
	 */
	public long cutOffPointMax;
	
	public Measure(long value, String name, long cutOffPointMax)
	{
		this.value = value;
		this.name = name;
		this.cutOffPointMax = cutOffPointMax;
	}
	
	/**
	 * Initialise the Measure at 0.
	 * @param name
	 * @param cutOffPointMax
	 */
	public Measure(String name, long cutOffPointMax)
	{
		this.value = 0;
		this.name = name;
		this.cutOffPointMax = cutOffPointMax;
	}
	
	/**
	 * Increments the value by valueToAdd.
	 * @param valueToAdd
	 */
	public final void addToValue(long valueToAdd)
	{
		this.value += valueToAdd;
	}
	
	/**
	 * Decrements value by valueToSubtract.
	 * @param valueToSubtract
	 */
	public final void subtractFromValue(long valueToSubtract)
	{
		this.value += valueToSubtract;
	}
	
	/**
	 * Sets value to newValue.
	 * @param newValue
	 */
	public final void setValue(long newValue)
	{
		this.value = newValue;
	}
	
	/**
	 * Returns this Measure's value.
	 * @return This measures value.
	 */
	public final long getValue()
	{
		return this.value;
	}
	
	/**
	 * Returns the name of the variable.
	 * @return
	 */
	public final String getName()
	{
		return this.name;
	}
}
