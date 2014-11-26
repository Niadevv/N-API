package co.uk.niadel.napi.util;

import java.util.*;

/**
 * Allows for a Map to store an expandable amount of values in the key.
 * @author Niadel
 */
public class ValueExpandableMap<K, V>
{
	/**
	 * The internal map where the data is stored.
	 */
	public Map<K, List<V>> dataMap = new HashMap<>();

	/**
	 * @see java.util.Map#put(Object, Object)
	 * @param key
	 * @param value
	 */
	public void put(K key, V value)
	{
		if (!this.dataMap.containsKey(key))
		{
			this.dataMap.put(key, Arrays.asList(value));
		}
		else
		{
			List<V> values = this.dataMap.get(key);
			values.add(value);
			this.dataMap.remove(key);
			this.dataMap.put(key, values);
		}
	}

	/**
	 * Adds multiple values for one key.
	 * @param key The key to put the values into.
	 * @param values The values to put in this map.
	 */
	public void putAll(K key, List<V> values)
	{
		for (V value : values)
		{
			this.put(key, value);
		}
	}

	/**
	 * Gets a value from the map.
	 * @param key The key to get the value of.
	 * @param valueIndex The index of the value you want.
	 * @return The value specified by key and valueIndex.
	 */
	public V get(K key, int valueIndex)
	{
		return this.dataMap.get(key).get(valueIndex);
	}

	/**
	 * Like Map.get, but gets all values assigned to that key.
	 * @param key The key to get the values of.
	 * @return The values matching key.
	 */
	public List<V> get(K key)
	{
		return this.dataMap.get(key);
	}

	/**
	 * @see java.util.Map#remove(Object)
	 */
	public void remove(K key)
	{
		this.dataMap.remove(key);
	}

	/**
	 * Gets a set containing the values of this map.
	 * @return A set containing the values of this map.
	 */
	public Set<List<V>> entrySet()
	{
		Iterator<Map.Entry<K, List<V>>> listIterator = this.dataMap.entrySet().iterator();
		Set<List<V>> setToReturn;
		List<List<V>> values = new ArrayList<>();

		while (listIterator.hasNext())
		{
			values.add(listIterator.next().getValue());
		}

		setToReturn = new HashSet<>(values);
		return setToReturn;
	}

	/**
	 * @see java.util.Map#keySet()
	 */
	public Set<K> keySet()
	{
		return this.dataMap.keySet();
	}

	/**
	 * @see java.util.Map#containsKey(Object)
	 */
	public boolean containsKey(K key)
	{
		return this.dataMap.containsKey(key);
	}

	/**
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty()
	{
		return this.dataMap.isEmpty();
	}

	/**
	 * @see java.util.Map#size()
	 */
	public int size()
	{
		return this.dataMap.size();
	}
}
