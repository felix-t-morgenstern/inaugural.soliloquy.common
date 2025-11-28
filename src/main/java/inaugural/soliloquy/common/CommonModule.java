package inaugural.soliloquy.common;

import inaugural.soliloquy.common.persistence.*;
import inaugural.soliloquy.tools.module.AbstractModule;
import soliloquy.specs.common.valueobjects.Coordinate2d;
import soliloquy.specs.common.valueobjects.Coordinate3d;
import soliloquy.specs.common.valueobjects.Pair;

import java.util.List;
import java.util.Map;

public class CommonModule extends AbstractModule {
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

        andRegister(persistenceHandler);
    }
}
