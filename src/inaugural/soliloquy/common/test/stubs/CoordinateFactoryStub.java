package inaugural.soliloquy.common.test.stubs;

import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.ICoordinateFactory;

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
