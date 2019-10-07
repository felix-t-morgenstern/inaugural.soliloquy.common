package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;

public class CoordinateStub implements Coordinate {
    private int _x;
    private int _y;

    @Override
    public int getX() {
        return _x;
    }

    @Override
    public void setX(int x) {
        _x = x;
    }

    @Override
    public int getY() {
        return _y;
    }

    @Override
    public void setY(int y) {
        _y = y;
    }

    @Override
    public ReadableCoordinate readOnlyRepresentation() {
        return null;
    }

    @Override
    public Coordinate makeClone() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return Coordinate.class.getCanonicalName();
    }

    @Override
    public int compareTo(ReadableCoordinate o) {
        return 0;
    }
}
