package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.*;

public class FakeMap<K,V> extends FakeReadableMap<K,V> implements Map<K,V> {

    public FakeMap() {

    }

    public FakeMap(K archetype1, V archetype2) {
        super(archetype1, archetype2);
    }

    @Override
    public void clear() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void put(K key, V value) throws IllegalArgumentException {
        _map.put(key, value);
    }

    @Override
    public void putAll(ReadableCollection<Pair<K, V>> items) throws IllegalArgumentException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public V removeByKey(K key) {
        return _map.remove(key);
    }

    @Override
    public boolean removeByKeyAndValue(K key, V value) {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Function<Pair<K, V>, String>> validators() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public ReadableMap<K, V> readOnlyRepresentation() {
        return new FakeReadableMap<>(_archetype1, _archetype2, _map);
    }
}
