package co.uk.niadel.napi.common.block;

/**
 * All mod redstone wire should implement this.
 * @author Niadel
 *
 */
public interface IRedstoneWire
{
	/**
	 * Returns this redstone wire's redstone level.
	 * @return This redstone wire's redstone level.
	 */
	public long getRedstoneLevel();
	
	/**
	 * Gets the most this wire's redstone level can be.
	 * @return The most this wire's redstone level can be.
	 */
	public long getMaxRedstoneLevel();
	
	/**
	 * Increases this block's power level.
	 * @param powerToIncreaseBy The amount to increase this blocks power level by.
	 */
	public void power(long powerToIncreaseBy);
	
	/**
	 * Called when the block is powered.
	 */
	public void onPower();
}
