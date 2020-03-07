package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;

public class FakeCollectionFactory implements CollectionFactory {
    @Override
    public <T> Collection<T> make(T archetype) throws IllegalArgumentException {
        return new FakeCollection<>(archetype);
    }

    @Override
    public <T> Collection<T> make(T[] items, T archetype) throws IllegalArgumentException {
        // Stub; not implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public String getInterfaceName() {
        // Stub method; unimplemented
        throw new UnsupportedOperationException();
    }
}
