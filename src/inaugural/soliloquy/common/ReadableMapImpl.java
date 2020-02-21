package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.*;

import java.util.HashMap;
import java.util.Iterator;

class ReadableMapImpl<K,V> extends HasTwoGenericParams<K,V> implements ReadableMap<K,V> {
    final HashMap<K,V> MAP;
    final K ARCHETYPE_1;
    final V ARCHETYPE_2;
    final PairFactory PAIR_FACTORY;
    final CollectionFactory COLLECTION_FACTORY;

    // TODO: Verify that archetypes are non-null
    ReadableMapImpl(K archetype1, V archetype2, PairFactory pairFactory,
                    CollectionFactory collectionFactory) {
        MAP = new HashMap<>();
        ARCHETYPE_1 = Check.ifNull(archetype1, "ReadableMapImpl", null, "archetype1");
        ARCHETYPE_2 = Check.ifNull(archetype2, "ReadableMapImpl", null, "archetype2");
        PAIR_FACTORY = Check.ifNull(pairFactory, "ReadableMapImpl", null, "pairFactory");
        COLLECTION_FACTORY = Check.ifNull(collectionFactory, "ReadableMapImpl", null,
                "collectionFactory");
    }

    // TODO: Verify that archetypes are non-null
    ReadableMapImpl(K archetype1, V archetype2, HashMap<K, V> values, PairFactory pairFactory,
                    CollectionFactory collectionFactory) {
        MAP = new HashMap<>();
        values.forEach(MAP::put);
        ARCHETYPE_1 = Check.ifNull(archetype1, "ReadableMapImpl", null, "archetype1");
        ARCHETYPE_2 = Check.ifNull(archetype2, "ReadableMapImpl", null, "archetype2");
        PAIR_FACTORY = Check.ifNull(pairFactory, "ReadableMapImpl", null, "pairFactory");
        COLLECTION_FACTORY = Check.ifNull(collectionFactory, "ReadableMapImpl", null,
                "collectionFactory");
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

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean equals(ReadableMap<K, V> map) throws IllegalArgumentException {
        Check.ifNull(map, "ReadableMapImpl", "equals", "map");
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
        return MAP.get(Check.ifNullOrEmptyIfString(id, "itemExists", "id", "id"));
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
        return MAP.containsKey(Check.ifNullOrEmptyIfString(key, "itemExists", "id", "key"));
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
