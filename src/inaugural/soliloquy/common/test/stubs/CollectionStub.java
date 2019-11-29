package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;

public class CollectionStub<V> extends ReadableCollectionStub<V> implements Collection<V> {
    CollectionStub() {
        super();
    }

    public CollectionStub(V archetype) {
        super(archetype);
    }

    @Override
    public void add(V item) throws UnsupportedOperationException {
        _collection.add(item);
    }

    @Override
    public void addAll(Collection<? extends V> items) throws UnsupportedOperationException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAll(V[] items) throws UnsupportedOperationException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() throws UnsupportedOperationException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeItem(V item) throws UnsupportedOperationException {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Function<V, String>> validators() {
        return null;
    }

    @Override
    public ReadableCollection<V> readOnlyRepresentation() {
        return new ReadableCollectionStub<>(_archetype, _collection);
    }
}
