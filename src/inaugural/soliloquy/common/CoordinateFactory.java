package inaugural.soliloquy.common;

import soliloquy.common.specs.ICoordinate;
import soliloquy.common.specs.ICoordinateFactory;

public class CoordinateFactory implements ICoordinateFactory {

	@Override
	public ICoordinate make(int x, int y) {
		return new Coordinate(x, y);
	}

}
