package inaugural.soliloquy.common.factories;

import inaugural.soliloquy.common.infrastructure.RegistryImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

public class RegistryFactoryImpl implements RegistryFactory {
    public RegistryFactoryImpl() {
    }

    @Override
    public <T extends HasId> Registry<T> make(T archetype) {
        return new RegistryImpl<>(Check.ifNull(archetype, "archetype"));
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
