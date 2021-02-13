package inaugural.soliloquy.common;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

public class RegistryFactoryImpl implements RegistryFactory {
    private final ListFactory LIST_FACTORY;

    public RegistryFactoryImpl(ListFactory listFactory) {
        LIST_FACTORY = Check.ifNull(listFactory, "listFactory");
    }

    @Override
    public <T extends HasId> Registry<T> make(T archetype) {
        return new RegistryImpl<>(Check.ifNull(archetype, "archetype"), LIST_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return RegistryFactory.class.getCanonicalName();
    }

    @Override
    public int hashCode() {
        return RegistryFactoryImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RegistryFactoryImpl && obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return RegistryFactoryImpl.class.getCanonicalName();
    }
}
