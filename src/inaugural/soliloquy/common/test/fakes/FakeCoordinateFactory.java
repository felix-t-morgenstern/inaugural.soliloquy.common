package inaugural.soliloquy.common.test.fakes;

import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.valueobjects.Coordinate;

public class FakeCoordinateFactory implements CoordinateFactory {
    @Override
    public Coordinate make(int x, int y) {
        Coordinate coordinate = new FakeCoordinate();
        coordinate.setX(x);
        coordinate.setY(y);
        return coordinate;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
