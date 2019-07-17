package inaugural.soliloquy.common;

import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.valueobjects.Coordinate;

public class CoordinateFactoryImpl implements CoordinateFactory {

	@Override
	public Coordinate make(int x, int y) {
		return new CoordinateImpl(x, y);
	}

	@Override
	public String getInterfaceName() {
		return CoordinateFactory.class.getCanonicalName();
	}

}
