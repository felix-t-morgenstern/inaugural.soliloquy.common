package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

public class RegistryFactoryImpl implements RegistryFactory {
    @Override
    public <T extends HasId> Registry<T> make(T archetype) {
        if (archetype == null) {
            throw new IllegalArgumentException(
                    "RegistryFactoryImpl.make: archetype must be non-null");
        }
        return new RegistryImpl<>(archetype);
    }

    @Override
    public String getInterfaceName() {
        return RegistryFactory.class.getCanonicalName();
    }
}
