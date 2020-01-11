package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

public class RegistryFactoryImpl implements RegistryFactory {
    private final CollectionFactory COLLECTION_FACTORY;

    @SuppressWarnings("ConstantConditions")
    public RegistryFactoryImpl(CollectionFactory collectionFactory) {
        if (collectionFactory == null) {
            throw new IllegalArgumentException(
                    "RegistryFactoryImpl: collectionFactory cannot be null");
        }
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public <T extends HasId> Registry<T> make(T archetype) {
        if (archetype == null) {
            throw new IllegalArgumentException(
                    "RegistryFactoryImpl.make: archetype must be non-null");
        }
        return new RegistryImpl<>(archetype, COLLECTION_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return RegistryFactory.class.getCanonicalName();
    }
}
