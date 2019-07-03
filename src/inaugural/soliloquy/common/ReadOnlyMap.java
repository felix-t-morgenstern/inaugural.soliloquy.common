package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.factories.IPairFactory;
import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.common.infrastructure.IPair;
import soliloquy.specs.common.infrastructure.IReadOnlyMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class ReadOnlyMap<K,V> extends HasTwoGenericParams<K,V> implements IReadOnlyMap<K,V> {
    final HashMap<K,V> MAP;
    final K ARCHETYPE_1;
    final V ARCHETYPE_2;
    final IPairFactory PAIR_FACTORY;
    final ICollectionFactory COLLECTION_FACTORY;

    @SuppressWarnings("unchecked")
    ReadOnlyMap(K archetype1, V archetype2, IPairFactory pairFactory,
                ICollectionFactory collectionFactory) {
        MAP = new HashMap<>();
        ARCHETYPE_1 = archetype1;
        ARCHETYPE_2 = archetype2;
        PAIR_FACTORY = pairFactory;
        COLLECTION_FACTORY = collectionFactory;
    }

    ReadOnlyMap(K archetype1, V archetype2, HashMap<K, V> values, IPairFactory pairFactory,
                ICollectionFactory collectionFactory) {
        MAP = new HashMap<>();
        values.forEach(MAP::put);
        ARCHETYPE_1 = archetype1;
        ARCHETYPE_2 = archetype2;
        PAIR_FACTORY = pairFactory;
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return MAP.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        return MAP.containsValue(value);
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
        if (items.size() != MAP.size()) return false;
        for(V item : items) {
            if (!MAP.containsValue(item)) return false;
        }
        return true;
    }

    @Override
    public boolean equals(IReadOnlyMap<K, V> map) throws IllegalArgumentException {
        if (map == null) {
            throw new IllegalArgumentException("Map.equals(IMap): comparator map cannot be null");
        }
        if (this.size() != map.size()) {
            return false;
        }
        for (K key : MAP.keySet()) {
            if (MAP.get(key) != map.get(key)) {
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
        return MAP.get(id);
    }

    @Override
    public ICollection<K> getKeys() {
        ICollection<K> idsCollection = new Collection<>(ARCHETYPE_1);
        for (K key : MAP.keySet()) {
            idsCollection.add(key);
        }
        return idsCollection;
    }

    @Override
    public ICollection<V> getValues() {
        ICollection<V> valuesCollection = new Collection<>(ARCHETYPE_2);
        for (V value : MAP.values()) {
            valuesCollection.add(value);
        }
        return valuesCollection;
    }

    @Override
    public ICollection<K> indicesOf(V item) {
        ICollection<K> keys = new Collection<>(ARCHETYPE_1);
        for(K key : getKeys()) {
            if (MAP.get(key) == item) {
                keys.add(key);
            }
        }
        return keys;
    }

    @Override
    public boolean isEmpty() {
        return MAP.isEmpty();
    }

    @Override
    public boolean itemExists(K key) {
        return MAP.containsKey(key);
    }

    @Override
    public int size() {
        return MAP.size();
    }

    @Override
    public Iterator<IPair<K,V>> iterator() {
        return new MapIterator(MAP, PAIR_FACTORY);
    }

    private class MapIterator implements Iterator<IPair<K,V>> {
        private Iterator<Map.Entry<K,V>> _hashMapIterator;
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
            Map.Entry<K,V> entry = _hashMapIterator.next();
            return _pairFactory.make(entry.getKey(), entry.getValue());
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public IMap<K, V> makeClone() {
        HashMap<K,V> clonedInternalMap = (HashMap<K,V>) MAP.clone();
        return new inaugural.soliloquy.common.Map<>(PAIR_FACTORY, ARCHETYPE_1, ARCHETYPE_2,
                COLLECTION_FACTORY, clonedInternalMap);
    }

    @Override
    public K getFirstArchetype() {
        return ARCHETYPE_1;
    }

    @Override
    public V getSecondArchetype() {
        return ARCHETYPE_2;
    }
}
