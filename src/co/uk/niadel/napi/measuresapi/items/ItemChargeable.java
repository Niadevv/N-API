package co.uk.niadel.napi.measuresapi.items;

import co.uk.niadel.napi.measuresapi.ModEnergyMeasure;
import co.uk.niadel.napi.measuresapi.ModMeasureBase;
import net.minecraft.item.Item;

/**
 * An item that can be charged with energy or liquid in some way.
 */
public abstract class ItemChargeable extends Item
{
	/**
	 * The measure of how charged this item is.
	 */
	public ModMeasureBase charge;

	public ItemChargeable(ModMeasureBase chargeMeasure)
	{
		this.charge = chargeMeasure;
	}

	/**
	 * Charges this item.
	 * @param amountToChargeBy How much to charge this item by.
	 */
	public void charge(long amountToChargeBy)
	{
		this.charge.incrementMeasure(amountToChargeBy);
	}

	/**
	 * Drains this item
	 * @param amountToDrain How much to drain this item by.
	 */
	public void drain(long amountToDrain)
	{
		this.charge.decrementMeasure(amountToDrain);
	}
}
