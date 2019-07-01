package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IMap;
import soliloquy.specs.common.infrastructure.IPair;
import soliloquy.specs.common.infrastructure.IReadOnlyMap;

import java.util.Iterator;

public class ReadOnlyMapStub<K,V> implements IReadOnlyMap<K,V> {
    @Override
    public boolean containsKey(K k) {
        return false;
    }

    @Override
    public boolean containsValue(V v) {
        return false;
    }

    @Override
    public boolean contains(IPair<K, V> iPair) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean equals(ICollection<V> iCollection) throws IllegalArgumentException {
        return false;
    }

    @Override
    public boolean equals(IReadOnlyMap<K, V> iReadOnlyMap) throws IllegalArgumentException {
        return false;
    }

    @Override
    public V get(K k) throws IllegalArgumentException, IllegalStateException {
        return null;
    }

    @Override
    public ICollection<K> getKeys() {
        return null;
    }

    @Override
    public ICollection<V> getValues() {
        return null;
    }

    @Override
    public ICollection<K> indicesOf(V v) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean itemExists(K k) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<IPair<K, V>> iterator() {
        return null;
    }

    @Override
    public IMap<K, V> makeClone() {
        return null;
    }

    @Override
    public K getFirstArchetype() throws IllegalStateException {
        return null;
    }

    @Override
    public V getSecondArchetype() throws IllegalStateException {
        return null;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
