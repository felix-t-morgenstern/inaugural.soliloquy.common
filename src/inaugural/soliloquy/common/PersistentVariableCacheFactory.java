package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.common.factories.IPersistentVariableCacheFactory;
import soliloquy.specs.common.infrastructure.IPersistentVariableCache;

public class PersistentVariableCacheFactory implements IPersistentVariableCacheFactory {
    private final ICollectionFactory COLLECTION_FACTORY;
    private final IMapFactory MAP_FACTORY;

    public PersistentVariableCacheFactory(ICollectionFactory collectionFactory,
                                          IMapFactory mapFactory) {
        COLLECTION_FACTORY = collectionFactory;
        MAP_FACTORY = mapFactory;
    }

    @Override
    public IPersistentVariableCache make() {
        return new PersistentVariableCache(COLLECTION_FACTORY, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return IPersistentVariableCacheFactory.class.getCanonicalName();
    }
}
