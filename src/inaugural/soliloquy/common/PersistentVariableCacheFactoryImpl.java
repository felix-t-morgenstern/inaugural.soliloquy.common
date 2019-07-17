package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PersistentVariableCacheFactory;
import soliloquy.specs.common.infrastructure.PersistentVariableCache;

public class PersistentVariableCacheFactoryImpl implements PersistentVariableCacheFactory {
    private final CollectionFactory COLLECTION_FACTORY;
    private final MapFactory MAP_FACTORY;

    public PersistentVariableCacheFactoryImpl(CollectionFactory collectionFactory,
                                              MapFactory mapFactory) {
        COLLECTION_FACTORY = collectionFactory;
        MAP_FACTORY = mapFactory;
    }

    @Override
    public PersistentVariableCache make() {
        return new PersistentVariableCacheImpl(COLLECTION_FACTORY, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return PersistentVariableCacheFactory.class.getCanonicalName();
    }
}
