package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.shared.SoliloquyClass;

import java.util.ArrayList;
import java.util.Iterator;

public class FakeReadableCollection<V> implements ReadableCollection<V> {
    ArrayList<V> _collection = new ArrayList<>();
    V _archetype;

    FakeReadableCollection() {

    }

    FakeReadableCollection(V archetype) {
        _archetype = archetype;
    }

    FakeReadableCollection(V archetype, ArrayList<V> values) {
        _archetype = archetype;
        _collection.addAll(values);
    }

    @Override
    public boolean contains(V item) {
        return _collection.contains(item);
    }

    @Override
    public boolean equals(ReadableCollection<V> items) {
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
        return _collection.toArray();
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
        Collection<V> collection = new FakeCollection<>();
        _collection.forEach(collection::add);
        return collection;
    }

    @Override
    public V getArchetype() {
        return _archetype;
    }

    @Override
    public String getInterfaceName() {
        return Collection.class.getCanonicalName() + "<" +
                (_archetype instanceof SoliloquyClass ?
                        ((SoliloquyClass)_archetype).getInterfaceName() :
                        _archetype.getClass().getCanonicalName())
                + ">";
    }
}
