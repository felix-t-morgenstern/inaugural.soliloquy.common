package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;

import java.util.Collection;

public class FakeListFactory implements ListFactory {
    @SuppressWarnings("rawtypes")
    public List NextListToReturn;
    @SuppressWarnings("rawtypes")
    public Collection MakeCollectionInput;
    public Object MakeArchetypeInput;

    @Override
    public <T> List<T> make(T archetype) throws IllegalArgumentException {
        return new FakeList<>(archetype);
    }

    @Override
    public <T> List<T> make(T[] items, T archetype) throws IllegalArgumentException {
        // Stub; not implemented
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> List<V> make(Collection<V> collection, V v) throws IllegalArgumentException {
        if (NextListToReturn != null) {
            MakeCollectionInput = collection;
            MakeArchetypeInput = v;
            return (List<V>) NextListToReturn;
        }
        return new FakeList<>(collection);
    }

    @Override
    public String getInterfaceName() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }
}
