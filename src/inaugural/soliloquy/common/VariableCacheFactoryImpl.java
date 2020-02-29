package inaugural.soliloquy.common;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;

public class VariableCacheFactoryImpl implements VariableCacheFactory {
    private final CollectionFactory COLLECTION_FACTORY;
    private final MapFactory MAP_FACTORY;

    public VariableCacheFactoryImpl(CollectionFactory collectionFactory,
                                    MapFactory mapFactory) {
        COLLECTION_FACTORY = Check.ifNull(collectionFactory, "collectionFactory");
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
    }

    @Override
    public VariableCache make() {
        return new VariableCacheImpl(COLLECTION_FACTORY, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return VariableCacheFactory.class.getCanonicalName();
    }
}
