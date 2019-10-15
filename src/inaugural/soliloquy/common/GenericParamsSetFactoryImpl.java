package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.PersistentValuesHandler;

public class GenericParamsSetFactoryImpl implements GenericParamsSetFactory {
    private PersistentValuesHandler _persistentValuesHandler;
    private MapFactory _mapFactory;

    public GenericParamsSetFactoryImpl(PersistentValuesHandler persistentValuesHandler, MapFactory mapFactory) {
        _persistentValuesHandler = persistentValuesHandler;
        _mapFactory = mapFactory;
    }

    @Override
    public GenericParamsSet make() {
        return new GenericParamsSetImpl(_persistentValuesHandler, _mapFactory);
    }

    @Override
    public String getInterfaceName() {
        return GenericParamsSetFactory.class.getCanonicalName();
    }

}
