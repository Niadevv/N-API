package co.uk.niadel.mpi.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Improved version of Map, allowing you to get the key by the value. Used by RecipesRegistry.
 * @author Niadel
 *
 * @param <K>
 * @param <V>
 */
public class DoubleMap<K, V>
{
	/**
	 * The Map with the key first.
	 */
	public Map<Entry, Entry> map1 = new HashMap<>();
	
	/**
	 * The Map with the value first.
	 */
	public Map<Entry, Entry> map2 = new HashMap<>();
	
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
	
	private V getByKey(Entry getValue)
	{
		return (V) map1.get(getValue);
	}
	
	private K getByValue(Entry getValue)
	{
		return (K) map2.get(getValue);
	}
	
	/**
	 * Class that is the representation of the entry and key.
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
