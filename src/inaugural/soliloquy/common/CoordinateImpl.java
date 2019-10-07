package inaugural.soliloquy.common;

import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.common.valueobjects.ReadableCoordinate;

public class CoordinateImpl extends ReadableCoordinateImpl implements Coordinate {
	
	public CoordinateImpl(int x, int y) {
		super(x,y);
	}

	@Override
	public void setX(int x) {
		_x = x;
	}

	@Override
	public void setY(int y) {
		_y = y;
	}

	@Override
	public ReadableCoordinate readOnlyRepresentation() {
		return new ReadableCoordinateImpl(_x, _y);
	}

	@Override
	public String getInterfaceName() {
		return Coordinate.class.getCanonicalName();
	}
}
