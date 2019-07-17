package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.valueobjects.Coordinate;

public class CoordinateFactoryStub implements CoordinateFactory {
    @Override
    public Coordinate make(int x, int y) {
        Coordinate coordinate = new CoordinateStub();
        coordinate.setX(x);
        coordinate.setY(y);
        return coordinate;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
