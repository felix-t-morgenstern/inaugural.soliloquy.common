package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.Coordinate;

public class FakePersistentCoordinateHandler
        implements PersistentValueTypeHandler<Coordinate> {
    @Override
    public Coordinate read(String s) throws IllegalArgumentException {
        return null;
    }

    @Override
    public String write(Coordinate coordinate) {
        return null;
    }

    @Override
    public Coordinate getArchetype() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
