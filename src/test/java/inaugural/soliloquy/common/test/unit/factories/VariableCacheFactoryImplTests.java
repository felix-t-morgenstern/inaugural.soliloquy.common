package inaugural.soliloquy.common.test.unit.factories;

import inaugural.soliloquy.common.factories.VariableCacheFactoryImpl;
import inaugural.soliloquy.common.infrastructure.VariableCacheImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.VariableCacheFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class VariableCacheFactoryImplTests {

    private VariableCacheFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new VariableCacheFactoryImpl();
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(VariableCacheFactory.class.getCanonicalName(),
                factory.getInterfaceName());
    }

    @Test
    public void testMake() {
        var output = factory.make();

        assertNotNull(output);
        assertTrue(output instanceof VariableCacheImpl);
    }

    @Test
    public void testHashCode() {
        assertEquals(VariableCacheFactoryImpl.class.getCanonicalName().hashCode(), factory.hashCode());
    }

    @Test
    public void testEquals() {
        VariableCacheFactory equalVariableCacheFactory = new VariableCacheFactoryImpl();
        VariableCacheFactory unequalVariableCacheFactory = mock(VariableCacheFactory.class);

        assertEquals(factory, equalVariableCacheFactory);
        assertNotEquals(factory, unequalVariableCacheFactory);
        assertNotEquals(null, factory);
    }

    @Test
    public void testToString() {
        assertEquals(VariableCacheFactoryImpl.class.getCanonicalName(), factory.toString());
    }
}
