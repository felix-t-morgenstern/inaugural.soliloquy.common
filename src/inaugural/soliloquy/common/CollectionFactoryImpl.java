package inaugural.soliloquy.common;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;

public class CollectionFactoryImpl implements CollectionFactory {

    @Override
    public <T> Collection<T> make(T archetype) {
        Check.archetypeAndArchetypesOfArchetypeAreNotNull("archetype", archetype);
        return new CollectionImpl<>(archetype);
    }

    @Override
    public <T> Collection<T> make(T[] items, T archetype) throws IllegalArgumentException {
        return new CollectionImpl<>(items, archetype);
    }

    @Override
    public String getInterfaceName() {
        return CollectionFactory.class.getCanonicalName();
    }

    @Override
    public int hashCode() {
        return CollectionFactoryImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CollectionFactoryImpl && obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return CollectionFactoryImpl.class.getCanonicalName();
    }
}
