package inaugural.soliloquy.common;

import com.google.inject.AbstractModule;
import inaugural.soliloquy.common.persistence.*;
import soliloquy.specs.common.persistence.PersistenceHandler;

public class CommonModule extends AbstractModule {
    private final PersistenceHandler PERSISTENCE_HANDLER;

    // TODO: Refactor away from service location pattern when JDK 13 is released
    // NB: JDK 12 results in "Illegal reflective access" errors thrown by Guice; a Google support
    //     technician has stated that this error will require workarounds until JDK 13 is released,
    //     but no workarounds (in that thread or elsewhere) have worked to resolve the issue.
    //     (The issue is that refactoring to true DI instead of service location requires the new
    //     injector to produce the PersistenceHandler, to manage the recursive dependencies
    //     with MapHandler, CollectionHandler, etc. This may also be a code smell.)
    //     [Source: https://github.com/google/guice/issues/1133]
    public CommonModule() {
        PERSISTENCE_HANDLER = new PersistenceHandlerImpl();

        var booleanHandler = new BooleanHandler();
        var coordinate2dHandler = new Coordinate2dHandler();
        var coordinate3dHandler = new Coordinate3dHandler();
        var uuidHandler = new UuidHandler();
        var integerHandler = new IntegerHandler();
        var stringHandler = new StringHandler();
        var mapHandler = new MapHandler(PERSISTENCE_HANDLER);
        var pairHandler = new PairHandler(PERSISTENCE_HANDLER);

        PERSISTENCE_HANDLER.addTypeHandler(booleanHandler);
        PERSISTENCE_HANDLER.addTypeHandler(coordinate2dHandler);
        PERSISTENCE_HANDLER.addTypeHandler(coordinate3dHandler);
        PERSISTENCE_HANDLER.addTypeHandler(uuidHandler);
        PERSISTENCE_HANDLER.addTypeHandler(integerHandler);
        PERSISTENCE_HANDLER.addTypeHandler(stringHandler);
        PERSISTENCE_HANDLER.addTypeHandler(mapHandler);
        PERSISTENCE_HANDLER.addTypeHandler(pairHandler);
    }

    @Override
    protected void configure() {
        bind(PersistenceHandler.class).toInstance(PERSISTENCE_HANDLER);
    }
}
