package co.uk.niadel.napi.gen.layers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.GenLayer;
import co.uk.niadel.napi.annotations.Internal;
import co.uk.niadel.napi.util.NAPILogHelper;

/**
 * Base for gen layers. Not entirely sure what this is for, but hey.
 * @author Niadel
 *
 */
public final class GenLayerRegistry extends GenLayer 
{
	/**
	 * Map that is indexed by a mod specific id.
	 */
	public static Map<String, IGenLayer> modLayers = new HashMap<>();
	
	/**
	 * Junk method made solely to satisfy Eclipse.
	 * @param par1
	 */
	private GenLayerRegistry(long par1)
	{
		super(par1);
	}
	
	/**
	 * Registers a gen layer.
	 * @param modId
	 * @param modGenLayer
	 */
	public static final void registerGenLayer(String modId, IGenLayer modGenLayer)
	{
		modLayers.put(modId, modGenLayer);
	}
	
	/**
	 * Iterates through all layers and calls their layerInit method.
	 * @return
	 */
	@Internal
	public static final IGenLayer[] iterateLayers(long seed, WorldType worldType)
	{
		IGenLayer[] layers = new IGenLayer[1000];
		
		if (!modLayers.isEmpty())
		{
			Iterator<Map.Entry<String, IGenLayer>> layerIterator = modLayers.entrySet().iterator();
			int i = 0;
			
			while (layerIterator.hasNext())
			{
				i += 1;
				IGenLayer currLayer = layerIterator.next().getValue();
				layers[i] = currLayer;
				currLayer.layerInit(seed, worldType);
			}
		}
		
		return layers;
	}

	@Override
	public int[] getInts(int par1, int par2, int par3, int par4)
	{
		NAPILogHelper.instance.log("GenLayerRegistry.getInts called! Ignore this, you can blame GenLayer for having getInts as being abstract :P");
		return null;
	}
}
