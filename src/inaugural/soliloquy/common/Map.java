package inaugural.soliloquy.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import inaugural.soliloquy.common.archetypes.MapValidatorFunctionArchetype;
import soliloquy.specs.common.entities.IFunction;
import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.factories.IPairFactory;
import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.IMap;
import soliloquy.specs.common.valueobjects.IPair;

public class Map<K,V> extends HasTwoGenericParams<K,V> implements IMap<K,V> {
	private HashMap<K,V> _map = new HashMap<>();

	private final IPairFactory PAIR_FACTORY;
	private final K ARCHETYPE_1;
	private final V ARCHETYPE_2;
	private final ICollection<IFunction<IPair<K,V>,String>> VALIDATORS;
	
	@SuppressWarnings("unchecked")
	public Map(IPairFactory pairFactory, K archetype1, V archetype2,
			   ICollectionFactory collectionFactory) {
		PAIR_FACTORY = pairFactory;
		ARCHETYPE_1 = archetype1;
		ARCHETYPE_2 = archetype2;
		VALIDATORS = collectionFactory.make(
				new MapValidatorFunctionArchetype(ARCHETYPE_1, ARCHETYPE_2));
	}
	
	private Map(IPairFactory pairFactory, K archetype1, V archetype2,
				ICollection<IFunction<IPair<K,V>,String>> validators, HashMap<K,V> internalMap) {
		PAIR_FACTORY = pairFactory;
		ARCHETYPE_1 = archetype1;
		ARCHETYPE_2 = archetype2;
		VALIDATORS = validators.makeClone();
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
		if (items == null) {
			throw new IllegalArgumentException("Map.equals(ICollection): comparator collection cannot be null");
		}
		if (items.size() != _map.size()) return false;
		for(V item : items) {
			if (!_map.containsValue(item)) return false;
		}
		return true;
	}

	@Override
	public boolean equals(IMap<K, V> map) throws IllegalArgumentException {
		if (map == null) {
			throw new IllegalArgumentException("Map.equals(IMap): comparator map cannot be null");
		}
		if (this.size() != map.size()) {
			return false;
		}
		for (K key : _map.keySet()) {
			if (_map.get(key) != map.get(key)) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public V get(K id) throws IllegalArgumentException, IllegalStateException {
		if (id == null) {
			throw new IllegalArgumentException("Map.get: null is an illegal Id");
		}
		if (id == "") {
			throw new IllegalArgumentException("Map.get: Blank string is an illegal Id");
		}
		return _map.get(id);
	}

	@Override
	public ICollection<K> getKeys() {
		ICollection<K> idsCollection = new Collection<>(ARCHETYPE_1);
		for (K key : _map.keySet()) {
			idsCollection.add(key);
		}
		return idsCollection;
	}

	@Override
	public ICollection<V> getValues() {
		ICollection<V> valuesCollection = new Collection<>(ARCHETYPE_2);
		for (V value : _map.values()) {
			valuesCollection.add(value);
		}
		return valuesCollection;
	}

	@Override
	public ICollection<K> indicesOf(V item) {
		ICollection<K> keys = new Collection<>(ARCHETYPE_1);
		for(K key : getKeys()) {
			if (_map.get(key) == item) {
				keys.add(key);
			}
		}
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

	@SuppressWarnings("ConstantConditions")
	@Override
	public void put(K key, V value) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("Map.put: Key to a Map cannot be null");
		}
		if (key == "") {
			throw new IllegalArgumentException("Map.put: Blank string is an illegal key");
		}
		IPair<K,V> toInsert = PAIR_FACTORY.make(key, value, ARCHETYPE_1, ARCHETYPE_2);
		for(IFunction<IPair<K,V>, String> validator : VALIDATORS) {
			String exceptionMessage = validator.run(toInsert);
			if (exceptionMessage != null) {
				throw new IllegalArgumentException("Map.put: Input failed validation; " +
						exceptionMessage);
			}
		}
		_map.put(key, value);
	}

	@Override
	public void putAll(ICollection<IPair<K, V>> items) throws IllegalArgumentException {
		for(IPair<K,V> item : items) {
			put(item.getItem1(), item.getItem2());
		}
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
	public ICollection<IFunction<IPair<K, V>, String>> validators() {
		return VALIDATORS;
	}

	@Override
	public int size() {
		return _map.size();
	}

	@Override
	public K getFirstArchetype() {
		return ARCHETYPE_1;
	}

	@Override
	public V getSecondArchetype() {
		return ARCHETYPE_2;
	}

	@Override
	public String getUnparameterizedInterfaceName() {
		return IMap.class.getCanonicalName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public IMap<K, V> makeClone() {
		HashMap<K,V> clonedInternalMap = (HashMap<K,V>) _map.clone();
		return new Map<>(PAIR_FACTORY, ARCHETYPE_1, ARCHETYPE_2, VALIDATORS, clonedInternalMap);
	}

	@Override
	public Iterator<IPair<K,V>> iterator() {
		return new MapIterator(_map, PAIR_FACTORY);
	}
	
	private class MapIterator implements Iterator<IPair<K,V>> {
		private Iterator<Entry<K,V>> _hashMapIterator;
		private IPairFactory _pairFactory;
		
		MapIterator(HashMap<K,V> hashMap, IPairFactory pairFactory) {
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
