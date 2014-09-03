package co.uk.niadel.napi.util;

import java.util.HashMap;
import java.util.Map;
import co.uk.niadel.napi.annotations.VersionMarkingAnnotations.TestFeature;

/**
 * Improved version of Map, allowing you to get the key by the value. Used by RecipesRegistry. Apparently google commons
 * has a BiMap with similar functionality, but that's a foreign library and I prefer not to use those when possible. Plus,
 * I think you have to call that one's .inverse() function, meaning there's an extra step - this, however, doesn't have that.
 * Note that it's a bit iffy when both keys are the same type.
 * @author Niadel
 *
 * @param <K>
 * @param <V>
 */
@TestFeature(firstAppearance = "1.0")
public class DoubleMap<K, V>
{
	/**
	 * The Map with the key first.
	 */
	public Map<Entry<K>, Entry<V>> map1 = new HashMap<>();
	
	/**
	 * The Map with the value first.
	 */
	public Map<Entry<V>, Entry<K>> map2 = new HashMap<>();
	
	public void put(K key, V value)
	{
		map1.put(new Entry(key, true), new Entry(value, false));
		map2.put(new Entry(value, false), new Entry(key, true));
	}
	
	public <R> R get(Object getValue)
	{
		if (map1.containsKey(getValue))
		{
			return (R) getByKey(new Entry(getValue));
		}
		else
		{
			return (R) getByValue(new Entry(getValue));
		}
	}
	
	public V getByKey(Entry getValue)
	{
		return (V) map1.get(getValue);
	}
	
	public K getByValue(Entry getValue)
	{
		return (K) map2.get(getValue);
	}
	
	/**
	 * Class that is the representation of a DoubleMap entry.
	 * @author Niadel
	 *
	 * @param <V>
	 */
	@SuppressWarnings("hiding")
	public class Entry<V>
	{
		public Object theEntry;
		public boolean isKey;
		
		public Entry(V entry)
		{
			this(entry, false);
		}
		
		public Entry(V entry, boolean isKey)
		{
			theEntry = entry;
		}
		
		public String toString()
		{
			return theEntry.toString();
		}
		
		public boolean equals(Entry entry)
		{
			return entry.theEntry.toString() == this.theEntry.toString();
		}
		
		public V getObject()
		{
			return (V) theEntry;
		}
	}
}
