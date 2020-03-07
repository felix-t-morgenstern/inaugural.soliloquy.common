package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.VariableCacheFactoryImpl;
import inaugural.soliloquy.common.test.fakes.FakeCollectionFactory;
import inaugural.soliloquy.common.test.fakes.FakeMapFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.VariableCacheFactory;

import static org.junit.jupiter.api.Assertions.*;

class VariableCacheFactoryImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new FakeCollectionFactory();
    private final MapFactory MAP_FACTORY = new FakeMapFactory();

    private VariableCacheFactory _variableCacheFactory;

    @BeforeEach
    void setUp() {
        _variableCacheFactory = new VariableCacheFactoryImpl(COLLECTION_FACTORY,
                MAP_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new VariableCacheFactoryImpl(null, MAP_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new VariableCacheFactoryImpl(COLLECTION_FACTORY, null));
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
}
