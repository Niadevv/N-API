package co.uk.niadel.napi.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Improved version of Map, allowing you to get the key by the value. Apparently google commons has a BiMap with similar functionality,
 * but that's an external library and I prefer not to use those when possible. Plus, I think you have to call that one's .inverse()
 * function, meaning there's an extra step - this, however, doesn't have that. Note that it could be a bit iffy when both keys are
 * the same type.
 * @author Niadel
 *
 * @param <K> The key value.
 * @param <V> The entry value.
 */
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
		map1.put(new Entry<>(key, true), new Entry<>(value, false));
		map2.put(new Entry<>(value, false), new Entry<>(key, true));
	}
	
	public Object get(Object getValue)
	{
		if (map1.containsKey(getValue))
		{
			return getByKey(new Entry<>(getValue));
		}
		else
		{
			return getByValue(new Entry<>(getValue));
		}
	}
	
	public V getByKey(Entry getValue)
	{
		return map1.get(getValue).getObject();
	}
	
	public K getByValue(Entry getValue)
	{
		return map2.get(getValue).getObject();
	}
	
	/**
	 * Class that is the representation of a DoubleMap entry.
	 * @author Niadel
	 *
	 * @param <EV> The type this entry corresponds to. Stands for Entry Value FYI.
	 */
	public class Entry<EV>
	{
		public EV theEntry;
		public boolean isKey;
		
		public Entry(EV entry)
		{
			this(entry, false);
		}
		
		public Entry(EV entry, boolean isKey)
		{
			this.theEntry = entry;
			this.isKey = isKey;
		}

		@Override
		public String toString()
		{
			return theEntry.toString();
		}
		
		public boolean equals(Entry entry)
		{
			return entry.theEntry.toString() == this.theEntry.toString();
		}
		
		public EV getObject()
		{
			return this.theEntry;
		}
	}
}
