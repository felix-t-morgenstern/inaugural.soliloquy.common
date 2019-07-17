package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadOnlyCollection;

import java.util.ArrayList;
import java.util.Iterator;

public class ReadOnlyCollectionStub<V> implements ReadOnlyCollection<V> {
    ArrayList<V> _collection = new ArrayList<>();
    V _archetype;

    ReadOnlyCollectionStub() {

    }

    ReadOnlyCollectionStub(V archetype) {
        _archetype = archetype;
    }

    ReadOnlyCollectionStub(V archetype, ArrayList<V> values) {
        _archetype = archetype;
        _collection.addAll(values);
    }

    @Override
    public boolean contains(V item) {
        return _collection.contains(item);
    }

    @Override
    public boolean equals(ReadOnlyCollection<V> items) {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(int index) {
        return _collection.get(index);
    }

    @Override
    public boolean isEmpty() {
        return _collection.isEmpty();
    }

    @Override
    public Object[] toArray() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return _collection.size();
    }

    @Override
    public Iterator<V> iterator() {
        return _collection.iterator();
    }

    @Override
    public Collection<V> makeClone() {
        Collection<V> collection = new CollectionStub<>();
        _collection.forEach(collection::add);
        return collection;
    }

    @Override
    public V getArchetype() {
        return _archetype;
    }

    @Override
    public String getUnparameterizedInterfaceName() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return "soliloquy.common.specs.Collection<" + _archetype.getClass().getCanonicalName() + ">";
    }
}
