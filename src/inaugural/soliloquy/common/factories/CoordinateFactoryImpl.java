package inaugural.soliloquy.common.factories;

import inaugural.soliloquy.common.valueobjects.CoordinateImpl;
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

    @Override
    public int hashCode() {
        return CoordinateFactoryImpl.class.getCanonicalName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CoordinateFactoryImpl && obj.hashCode() == hashCode();
    }

    @Override
    public String toString() {
        return CoordinateFactoryImpl.class.getCanonicalName();
    }

}
