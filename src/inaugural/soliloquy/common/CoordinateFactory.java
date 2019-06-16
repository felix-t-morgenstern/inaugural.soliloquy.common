package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.ICoordinateFactory;
import soliloquy.specs.common.valueobjects.ICoordinate;

public class CoordinateFactory implements ICoordinateFactory {

	@Override
	public ICoordinate make(int x, int y) {
		return new Coordinate(x, y);
	}

	@Override
	public String getInterfaceName() {
		return ICoordinateFactory.class.getCanonicalName();
	}

}
