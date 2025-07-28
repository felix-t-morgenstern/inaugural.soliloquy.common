package inaugural.soliloquy.common;

import inaugural.soliloquy.common.persistence.*;
import inaugural.soliloquy.tools.Check;
import org.int4.dirk.api.Injector;
import org.int4.dirk.di.Injectors;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.game.Module;

import java.util.List;
import java.util.Map;

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

        persistenceHandler.addTypeHandler(Boolean.class, booleanHandler);
        persistenceHandler.addTypeHandler(Coordinate2d.class, coordinate2dHandler);
        persistenceHandler.addTypeHandler(Coordinate3d.class, coordinate3dHandler);
        persistenceHandler.addTypeHandler(Integer.class, integerHandler);
        persistenceHandler.addTypeHandler(String.class, stringHandler);
        persistenceHandler.addTypeHandler(List.class, listHandler);
        persistenceHandler.addTypeHandler(Map.class, mapHandler);
        persistenceHandler.addTypeHandler(Pair.class, pairHandler);

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
