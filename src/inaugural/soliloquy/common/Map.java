package inaugural.soliloquy.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IFunction;
import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPairFactory;

public class Map<K,V> extends HasTwoGenericParams<K,V> implements IMap<K,V> {
	protected HashMap<K,V> _map = new HashMap<K,V>();
	
	private IFunction<IPair<K,V>,String> _validator;
	private IPairFactory _pairFactory;
	private K _archetype1;
	private V _archetype2;
	
	public Map(IPairFactory pairFactory, K archetype1, V archetype2)
	{
		_pairFactory = pairFactory;
		_archetype1 = archetype1;
		_archetype2 = archetype2;
	}
	
	private Map(IPairFactory pairFactory, K archetype1, V archetype2, HashMap<K,V> internalMap)
	{
		_pairFactory = pairFactory;
		_archetype1 = archetype1;
		_archetype2 = archetype2;
		_map = internalMap;
	}

	@Override
	public void clear() {
		_map.clear();
	}

	@Override
	public boolean containsKey(K key) {
		return _map.containsKey(key);
	}

	@Override
	public boolean containsValue(V value) {
		return _map.containsValue(value);
	}

	@Override
	public boolean contains(IPair<K, V> item) throws IllegalArgumentException {
		return get(item.getItem1()) == item.getItem2();
	}

	@Override
	public boolean equals(ICollection<V> items) throws IllegalArgumentException {
		if (items == null) throw new IllegalArgumentException("comparator collection cannot be null");
		if (items.size() != _map.size()) return false;
		for(V item : items)
		{
			if (!_map.containsValue(item)) return false;
		}
		return true;
	}

	@Override
	public boolean equals(IMap<K, V> map) throws IllegalArgumentException {
		if (map == null) throw new IllegalArgumentException("comparator map cannot be null");
		if (this.size() != map.size()) return false;
		for (K key : _map.keySet())
		{
			if (_map.get(key) != map.get(key)) return false;
		}
		return true;
	}

	@Override
	public V get(K id) throws IllegalArgumentException, IllegalStateException {
		if (id == null) throw new IllegalArgumentException("null is an illegal Id");
		if (id instanceof String && (String)id == "") throw new IllegalArgumentException("Blank string is an illegal Id");
		return _map.get(id);
	}

	@Override
	public ICollection<K> getKeys() {
		ICollection<K> idsCollection = new Collection<K>(_archetype1);
		for (K key : _map.keySet())
		{
			idsCollection.add(key);
		}
		return idsCollection;
	}

	@Override
	public ICollection<V> getValues() {
		ICollection<V> valuesCollection = new Collection<V>(_archetype2);
		for (V value : _map.values())
		{
			valuesCollection.add(value);
		}
		return valuesCollection;
	}

	@Override
	public ICollection<K> indicesOf(V item) {
		ICollection<K> keys = new Collection<K>(_archetype1);
		for(K key : getKeys()) if (_map.get(key) == item) keys.add(key);
		return keys;
	}

	@Override
	public boolean isEmpty() {
		return _map.isEmpty();
	}

	@Override
	public boolean itemExists(K key) {
		return _map.containsKey(key);
	}

	@Override
	public void put(K key, V value) throws IllegalArgumentException {
		if (key == null) throw new IllegalArgumentException("Key to a Map cannot be null");
		if (key instanceof String && (String)key == "") throw new IllegalArgumentException("Blank string is an illegal key");
		if(_validator != null)
		{
			String validationMsg = _validator.run(new Pair<K,V>(key,value));
			if (validationMsg != null) throw new IllegalArgumentException(validationMsg);
		}
		_map.put(key, value);
	}

	@Override
	public void putAll(ICollection<IPair<K, V>> items) throws IllegalArgumentException {
		for(IPair<K,V> item : items) put(item.getItem1(), item.getItem2());
	}

	@Override
	public V removeByKey(K key) {
		return _map.remove(key);
	}

	@Override
	public boolean removeByKeyAndValue(K key, V value) {
		return _map.remove(key, value);
	}

	@Override
	public void setValidator(IFunction<IPair<K, V>, String> validator) {
		_validator = validator;
	}

	@Override
	public int size() {
		return _map.size();
	}

	@Override
	public K getFirstArchetype() {
		return _archetype1;
	}

	@Override
	public V getSecondArchetype() {
		return _archetype2;
	}

	@Override
	protected String getUnparameterizedClassName() {
		return "soliloquy.common.specs.IMap";
	}

	@SuppressWarnings("unchecked")
	@Override
	public IMap<K, V> makeClone() {
		HashMap<K,V> clonedInternalMap = (HashMap<K,V>) _map.clone();
		IMap<K,V> clonedMap = new Map<K,V>(null,_archetype1,_archetype2, clonedInternalMap);
		return clonedMap;
	}

	@Override
	public Iterator<IPair<K,V>> iterator() {
		return new MapIterator(_map,_pairFactory);
	}
	
	class MapIterator implements Iterator<IPair<K,V>>
	{
		private Iterator<Entry<K,V>> _hashMapIterator;
		private IPairFactory _pairFactory;
		
		MapIterator(HashMap<K,V> hashMap, IPairFactory pairFactory)
		{
			_hashMapIterator = hashMap.entrySet().iterator();
			_pairFactory = pairFactory;
		}
		
		@Override
		public boolean hasNext() {
			return _hashMapIterator.hasNext();
		}

		@Override
		public IPair<K, V> next() {
			Entry<K,V> entry = _hashMapIterator.next();
			return _pairFactory.make(entry.getKey(), entry.getValue());
		}
		
	}

}
