package co.uk.niadel.api.gen.layers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import co.uk.niadel.api.annotations.MPIAnnotations.Internal;
import net.minecraft.world.gen.layer.GenLayer;

/**
 * Base for gen layers. Not entirely sure what this is for, but hey.
 * @author Niadel
 *
 */
public abstract class GenLayerRegistry extends GenLayer 
{
	/**
	 * Map that is indexed by a mod specific id.
	 */
	public static Map<String, GenLayer> modLayers = new HashMap<>();
	
	/**
	 * Junk method made solely to satisfy Eclipse.
	 * @param par1
	 */
	public GenLayerRegistry(long par1) 
	{
		super(par1);
	}
	
	/**
	 * Registers a gen layer.
	 * @param modId
	 * @param modGenLayer
	 */
	public static final void registerGenLayer(String modId, GenLayer modGenLayer)
	{
		modLayers.put(modId, modGenLayer);
	}
	
	/**
	 * Iterates through all layers and calls their layerInit method.
	 * @return
	 */
	@Internal
	public static final IGenLayer[] iterateLayers()
	{
		IGenLayer[] layers = new IGenLayer[] {};
		
		if (!modLayers.isEmpty())
		{
			Iterator layerIterator = modLayers.entrySet().iterator();
			int i = 0;
			
			while (layerIterator.hasNext())
			{
				i += 1;
				IGenLayer currLayer = (IGenLayer) layerIterator.next();
				layers[i] = currLayer;
				currLayer.layerInit();
			}
		}
		
		return layers;
	}
}
