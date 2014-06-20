package co.uk.niadel.mpi.common.block;

/**
 * All mod redstone wire implements this.
 * @author Niadel
 *
 */
public interface IRedstoneWire
{
	/**
	 * Returns this redstone wire's redstone level.
	 * @return
	 */
	public long getRedstoneLevel();
	
	/**
	 * Gets the most this wire's redstone level can be.
	 * @return
	 */
	public long getMaxRedstoneLevel();
	
	/**
	 * Increases this block's power level.
	 * @param powerToIncreaseBy
	 */
	public void power(long powerToIncreaseBy);
	
	/**
	 * Called when the block is powered.
	 */
	public void onPower();
}
