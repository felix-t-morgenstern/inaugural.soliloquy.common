package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.ReadOnlyMap;

import java.util.HashMap;
import java.util.Iterator;

class ReadOnlyMapImpl<K,V> extends HasTwoGenericParams<K,V> implements ReadOnlyMap<K,V> {
    final HashMap<K,V> MAP;
    final K ARCHETYPE_1;
    final V ARCHETYPE_2;
    final PairFactory PAIR_FACTORY;
    final CollectionFactory COLLECTION_FACTORY;

    @SuppressWarnings("unchecked")
    ReadOnlyMapImpl(K archetype1, V archetype2, PairFactory pairFactory,
                    CollectionFactory collectionFactory) {
        MAP = new HashMap<>();
        ARCHETYPE_1 = archetype1;
        ARCHETYPE_2 = archetype2;
        PAIR_FACTORY = pairFactory;
        COLLECTION_FACTORY = collectionFactory;
    }

    ReadOnlyMapImpl(K archetype1, V archetype2, HashMap<K, V> values, PairFactory pairFactory,
                    CollectionFactory collectionFactory) {
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
    public boolean contains(Pair<K, V> item) throws IllegalArgumentException {
        return get(item.getItem1()) == item.getItem2();
    }

    @Override
    public boolean equals(Collection<V> items) throws IllegalArgumentException {
        if (items == null) {
            throw new IllegalArgumentException("Map.equals(Collection): comparator collection cannot be null");
        }
        if (items.size() != MAP.size()) return false;
        for(V item : items) {
            if (!MAP.containsValue(item)) return false;
        }
        return true;
    }

    @Override
    public boolean equals(ReadOnlyMap<K, V> map) throws IllegalArgumentException {
        if (map == null) {
            throw new IllegalArgumentException("Map.equals(Map): comparator map cannot be null");
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
    public Collection<K> getKeys() {
        Collection<K> idsCollection = new CollectionImpl<>(ARCHETYPE_1);
        for (K key : MAP.keySet()) {
            idsCollection.add(key);
        }
        return idsCollection;
    }

    @Override
    public Collection<V> getValues() {
        Collection<V> valuesCollection = new CollectionImpl<>(ARCHETYPE_2);
        for (V value : MAP.values()) {
            valuesCollection.add(value);
        }
        return valuesCollection;
    }

    @Override
    public Collection<K> indicesOf(V item) {
        Collection<K> keys = new CollectionImpl<>(ARCHETYPE_1);
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
    public Iterator<Pair<K,V>> iterator() {
        return new MapIterator(MAP, PAIR_FACTORY);
    }

    private class MapIterator implements Iterator<Pair<K,V>> {
        private Iterator<java.util.Map.Entry<K,V>> _hashMapIterator;
        private PairFactory _pairFactory;

        MapIterator(HashMap<K,V> hashMap, PairFactory pairFactory) {
            _hashMapIterator = hashMap.entrySet().iterator();
            _pairFactory = pairFactory;
        }

        @Override
        public boolean hasNext() {
            return _hashMapIterator.hasNext();
        }

        @Override
        public Pair<K, V> next() {
            java.util.Map.Entry<K,V> entry = _hashMapIterator.next();
            return _pairFactory.make(entry.getKey(), entry.getValue());
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<K, V> makeClone() {
        HashMap<K,V> clonedInternalMap = (HashMap<K,V>) MAP.clone();
        return new MapImpl<>(PAIR_FACTORY, ARCHETYPE_1, ARCHETYPE_2,
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
