package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.factories.IPairFactory;
import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.common.infrastructure.IPair;
import soliloquy.specs.common.infrastructure.IReadOnlyMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReadOnlyMapStub<K,V> implements IReadOnlyMap<K,V> {
    private final IPairFactory PAIR_FACTORY = new PairFactoryStub();

    HashMap<K,V> _map = new HashMap<>();
    K _archetype1;
    V _archetype2;

    ReadOnlyMapStub() {

    }

    ReadOnlyMapStub(K archetype1, V archetype2) {
        _archetype1 = archetype1;
        _archetype2 = archetype2;
    }

    ReadOnlyMapStub(K archetype1, V archetype2, HashMap<K,V> values) {
        _archetype1 = archetype1;
        _archetype2 = archetype2;
        values.forEach(_map::put);
    }

    @Override
    public boolean containsKey(K key) {
        return _map.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(IPair<K, V> item) throws IllegalArgumentException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(ICollection<V> items) throws IllegalArgumentException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(IReadOnlyMap<K, V> map) throws IllegalArgumentException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        try {
            IReadOnlyMap<K,V> map = (IReadOnlyMap<K,V>) o;
            if (map.size() != this.size()) {
                return false;
            }
            for (K key : _map.keySet()) {
                if (!_map.get(key).equals(map.get(key))) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public V get(K key) throws IllegalArgumentException, IllegalStateException {
        return _map.get(key);
    }

    @Override
    public ICollection<K> getKeys() {
        ICollection<K> keys = new CollectionStub<>();
        for (K key : _map.keySet())
        {
            keys.add(key);
        }
        return keys;
    }

    @Override
    public ICollection<V> getValues() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public ICollection<K> indicesOf(V item) {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return _map.isEmpty();
    }

    @Override
    public boolean itemExists(K key) {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return _map.size();
    }

    @Override
    public Iterator<IPair<K, V>> iterator() {
        return new MapStubIterator(_map, PAIR_FACTORY);
    }

    @Override
    public IMap<K, V> makeClone() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public K getFirstArchetype() throws IllegalStateException {
        return _archetype1;
    }

    @Override
    public V getSecondArchetype() throws IllegalStateException {
        return _archetype2;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    private class MapStubIterator implements Iterator<IPair<K,V>> {
        private Iterator<Map.Entry<K,V>> _hashMapIterator;
        private IPairFactory _pairFactory;

        MapStubIterator(HashMap<K,V> hashMap, IPairFactory pairFactory) {
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
}
