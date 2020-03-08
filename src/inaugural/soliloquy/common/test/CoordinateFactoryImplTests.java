package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.test.fakes.FakeCoordinateFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.common.CoordinateFactoryImpl;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.valueobjects.Coordinate;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateFactoryImplTests {
    private CoordinateFactoryImpl _coordinateFactory;

    @BeforeEach
    void setUp() {
        _coordinateFactory = new CoordinateFactoryImpl();
    }

    @Test
    void testMake() {
        Coordinate coordinate = _coordinateFactory.make(0,0);
        assertNotNull(coordinate);

        coordinate.setX(10);
        assertEquals(10, coordinate.getX());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(CoordinateFactory.class.getCanonicalName(),
                _coordinateFactory.getInterfaceName());
    }

    @Test
    void testHashCode() {
        assertEquals(CoordinateFactoryImpl.class.getCanonicalName().hashCode(),
                _coordinateFactory.hashCode());
    }

    @SuppressWarnings({"SimplifiableJUnitAssertion", "ConstantConditions"})
    @Test
    void testEquals() {
        CoordinateFactory equalCoordinateFactory = new CoordinateFactoryImpl();
        CoordinateFactory unequalCoordinateFactory = new FakeCoordinateFactory();

        assertTrue(_coordinateFactory.equals(equalCoordinateFactory));
        assertFalse(_coordinateFactory.equals(unequalCoordinateFactory));
        assertFalse(_coordinateFactory.equals(null));
    }

    @Test
    void testToString() {
        assertEquals(CoordinateFactoryImpl.class.getCanonicalName(),
                _coordinateFactory.toString());
    }
}
