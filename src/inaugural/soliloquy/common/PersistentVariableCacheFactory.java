package inaugural.soliloquy.common;

import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IPersistentVariableCache;
import soliloquy.common.specs.IPersistentVariableCacheFactory;

public class PersistentVariableCacheFactory implements IPersistentVariableCacheFactory {
    private final ICollectionFactory COLLECTION_FACTORY;

    public PersistentVariableCacheFactory(ICollectionFactory collectionFactory) {
        COLLECTION_FACTORY = collectionFactory;
    }

    @Override
    public IPersistentVariableCache make() {
        return new PersistentVariableCache(COLLECTION_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return IPersistentVariableCacheFactory.class.getCanonicalName();
    }
}
