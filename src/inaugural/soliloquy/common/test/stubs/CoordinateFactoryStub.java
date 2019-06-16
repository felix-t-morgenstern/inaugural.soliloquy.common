package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.factories.ICoordinateFactory;
import soliloquy.specs.common.valueobjects.ICoordinate;

public class CoordinateFactoryStub implements ICoordinateFactory {
    @Override
    public ICoordinate make(int x, int y) {
        ICoordinate coordinate = new CoordinateStub();
        coordinate.setX(x);
        coordinate.setY(y);
        return coordinate;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
