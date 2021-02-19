package inaugural.soliloquy.common.valueobjects;

import soliloquy.specs.common.valueobjects.Coordinate;

public class CoordinateImpl implements Coordinate {
    private int _x;
    private int _y;

    public CoordinateImpl(int x, int y) {
        _x = x;
        _y = y;
    }

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
    public int compareTo(Coordinate arg0) {
        return coordinateCount(this) - coordinateCount(arg0);
    }

    private int coordinateCount(Coordinate coordinate)    {
        int digitsSum = (coordinate.getX() + coordinate.getY());
        int coordinatesHitherto = (digitsSum * (digitsSum + 1))/2;
        return 1 + coordinatesHitherto + coordinate.getX();
    }

    @Override
    public String getInterfaceName() {
        return Coordinate.class.getCanonicalName();
    }

    @Override
    public Coordinate makeClone() {
        return new CoordinateImpl(_x, _y);
    }
}
