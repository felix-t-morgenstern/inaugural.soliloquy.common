package inaugural.soliloquy.common;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;
import soliloquy.specs.common.infrastructure.VariableCache;

public class VariableCacheFactoryImpl implements VariableCacheFactory {
    private final ListFactory LIST_FACTORY;
    private final MapFactory MAP_FACTORY;

    public VariableCacheFactoryImpl(ListFactory collectionFactory,
                                    MapFactory mapFactory) {
        LIST_FACTORY = Check.ifNull(collectionFactory, "collectionFactory");
        MAP_FACTORY = Check.ifNull(mapFactory, "mapFactory");
    }

    @Override
    public VariableCache make() {
        return new VariableCacheImpl(LIST_FACTORY, MAP_FACTORY);
    }

    @Override
    public String getInterfaceName() {
        return VariableCacheFactory.class.getCanonicalName();
    }

    @Override
    public int hashCode() {
        return VariableCacheFactoryImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VariableCacheFactoryImpl && obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return VariableCacheFactoryImpl.class.getCanonicalName();
    }
}
