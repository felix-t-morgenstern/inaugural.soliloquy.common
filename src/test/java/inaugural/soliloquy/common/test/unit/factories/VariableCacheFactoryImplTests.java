package inaugural.soliloquy.common.test.unit.factories;

import inaugural.soliloquy.common.factories.VariableCacheFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class VariableCacheFactoryImplTests {

    private VariableCacheFactory _variableCacheFactory;

    @BeforeEach
    void setUp() {
        _variableCacheFactory = new VariableCacheFactoryImpl();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(VariableCacheFactory.class.getCanonicalName(),
                _variableCacheFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_variableCacheFactory.make());
    }

    @Test
    void testHashCode() {
        assertEquals(VariableCacheFactoryImpl.class.getCanonicalName().hashCode(),
                _variableCacheFactory.hashCode());
    }

    @Test
    void testEquals() {
        VariableCacheFactory equalVariableCacheFactory = new VariableCacheFactoryImpl();
        VariableCacheFactory unequalVariableCacheFactory = mock(VariableCacheFactory.class);

        assertEquals(_variableCacheFactory, equalVariableCacheFactory);
        assertNotEquals(_variableCacheFactory, unequalVariableCacheFactory);
        assertNotEquals(null, _variableCacheFactory);
    }

    @Test
    void testToString() {
        assertEquals(VariableCacheFactoryImpl.class.getCanonicalName(),
                _variableCacheFactory.toString());
    }
}
