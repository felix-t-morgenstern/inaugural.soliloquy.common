package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;

import java.util.Collection;

public class ListFactoryImpl implements ListFactory {

    @Override
    public <T> List<T> make(T archetype) {
        return new ListImpl<>(archetype);
    }

    // TODO: Ensure that items is not null
    @Override
    public <T> List<T> make(T[] items, T archetype) throws IllegalArgumentException {
        return new ListImpl<>(items, archetype);
    }

    // TODO: Ensure that items is not null
    @Override
    public <V> List<V> make(Collection<V> items, V archetype) throws IllegalArgumentException {
        return new ListImpl<>(items, archetype);
    }

    @Override
    public String getInterfaceName() {
        return ListFactory.class.getCanonicalName();
    }

    @Override
    public int hashCode() {
        return ListFactoryImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ListFactoryImpl && obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return ListFactoryImpl.class.getCanonicalName();
    }
}
