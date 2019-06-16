package inaugural.soliloquy.common.test.stubs;

import soliloquy.specs.common.valueobjects.ICoordinate;

public class CoordinateStub implements ICoordinate {
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
    public int compareTo(ICoordinate o) {
        return 0;
    }

    @Override
    public ICoordinate makeClone() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return ICoordinate.class.getCanonicalName();
    }
}
