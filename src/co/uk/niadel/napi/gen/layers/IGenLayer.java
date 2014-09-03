package co.uk.niadel.napi.gen.layers;

import net.minecraft.world.WorldType;

public interface IGenLayer
{
	/**
	 * The point where you initialise your GenLayer.
	 */
	public void layerInit(long seed, WorldType worldType);
}
