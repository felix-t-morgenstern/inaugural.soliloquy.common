package inaugural.soliloquy.common;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

public class RegistryFactoryImpl implements RegistryFactory {
    private final CollectionFactory COLLECTION_FACTORY;

    public RegistryFactoryImpl(CollectionFactory collectionFactory) {
        COLLECTION_FACTORY = Check.ifNull(collectionFactory, "collectionFactory");
    }

    @Override
    public <T extends HasId> Registry<T> make(T archetype) {
        return new RegistryImpl<>(Check.ifNull(archetype, "archetype"), COLLECTION_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return RegistryFactory.class.getCanonicalName();
    }
}
