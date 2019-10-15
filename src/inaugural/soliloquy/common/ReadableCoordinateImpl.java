package inaugural.soliloquy.common;

import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;

public class ReadableCoordinateImpl implements ReadableCoordinate {
    int _x;
    int _y;

    public ReadableCoordinateImpl(int x, int y){
        _x = x;
        _y = y;
    }

    @Override
    public int getX() {
        return _x;
    }

    @Override
    public int getY() {
        return _y;
    }

    @Override
    public int compareTo(ReadableCoordinate arg0) {
        return coordinateCount(this) - coordinateCount(arg0);
    }

    private int coordinateCount(ReadableCoordinate coordinate)    {
        int digitsSum = (coordinate.getX() + coordinate.getY());
        int coordinatesHitherto = (digitsSum * (digitsSum + 1))/2;
        return 1 + coordinatesHitherto + coordinate.getX();
    }

    @Override
    public Coordinate makeClone() {
        return new CoordinateImpl(getX(), getY());
    }

    @Override
    public String getInterfaceName() {
        return ReadableCoordinate.class.getCanonicalName();
    }
}
