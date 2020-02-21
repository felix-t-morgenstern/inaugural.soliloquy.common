package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

public class RegistryFactoryImpl implements RegistryFactory {
    private final CollectionFactory COLLECTION_FACTORY;

    public RegistryFactoryImpl(CollectionFactory collectionFactory) {
        COLLECTION_FACTORY = Check.ifNull(collectionFactory, "RegistryFactoryImpl", null,
                "collectionFactory");
    }

    @Override
    public <T extends HasId> Registry<T> make(T archetype) {
        return new RegistryImpl<>(Check.ifNull(archetype, "RegistryFactoryImpl", "make",
                "archetype"), COLLECTION_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return RegistryFactory.class.getCanonicalName();
    }
}
