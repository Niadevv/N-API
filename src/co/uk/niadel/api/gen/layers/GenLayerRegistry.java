package co.uk.niadel.api.gen.layers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
	
	public static final void registerGenLayer(String modId, GenLayer modGenLayer)
	{
		modLayers.put(modId, modGenLayer);
	}
	
	public static final GenLayer[] iterateLayers()
	{
		GenLayerRegistry[] layers = new GenLayerRegistry[] {};
		
		if (!modLayers.isEmpty())
		{
			Iterator layerIterator = modLayers.entrySet().iterator();
			int i = 0;
			
			while (layerIterator.hasNext())
			{
				i += 1;
				GenLayerRegistry currLayer = (GenLayerRegistry) layerIterator.next();
				layers[i] = currLayer;
				currLayer.layerInit();
			}
		}
		
		return layers;
	}
	
	/**
	 * This is where to perform special stuff for your layer, like modInit() for mods.
	 */
	public abstract void layerInit();
}
