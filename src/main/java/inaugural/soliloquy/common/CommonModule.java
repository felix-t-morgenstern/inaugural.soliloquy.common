package inaugural.soliloquy.common;

import inaugural.soliloquy.common.persistence.*;
import inaugural.soliloquy.tools.Check;
import org.int4.dirk.api.Injector;
import org.int4.dirk.di.Injectors;
import soliloquy.specs.game.Module;

public class CommonModule implements Module {
    private final Injector INJECTOR;

    public CommonModule() {
        var persistenceHandler = new PersistenceHandlerImpl();

        var booleanHandler = new BooleanHandler();
        var coordinate2dHandler = new Coordinate2dHandler();
        var coordinate3dHandler = new Coordinate3dHandler();
        var integerHandler = new IntegerHandler();
        var stringHandler = new StringHandler();
        var listHandler = new ListHandler(persistenceHandler);
        var mapHandler = new MapHandler(persistenceHandler);
        var pairHandler = new PairHandler(persistenceHandler);

        persistenceHandler.addTypeHandler(booleanHandler);
        persistenceHandler.addTypeHandler(coordinate2dHandler);
        persistenceHandler.addTypeHandler(coordinate3dHandler);
        persistenceHandler.addTypeHandler(integerHandler);
        persistenceHandler.addTypeHandler(stringHandler);
        persistenceHandler.addTypeHandler(listHandler);
        persistenceHandler.addTypeHandler(mapHandler);
        persistenceHandler.addTypeHandler(pairHandler);

        INJECTOR = Injectors.manual();

        INJECTOR.registerInstance(persistenceHandler);
    }

    @Override
    public <T> T provide(Class<T> clazz) throws IllegalArgumentException {
        return INJECTOR.getInstance(clazz);
    }

    public <T> T provide(String instance) throws IllegalArgumentException {
        Check.ifNullOrEmpty(instance, "instance");
        throw new IllegalArgumentException("No named instances within CommonModule");
    }
}
