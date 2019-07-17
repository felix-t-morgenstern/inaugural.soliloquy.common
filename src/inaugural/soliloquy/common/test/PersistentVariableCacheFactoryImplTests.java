package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.PersistentVariableCacheFactoryImpl;
import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.common.test.stubs.MapFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.factories.PersistentVariableCacheFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersistentVariableCacheFactoryImplTests {
    private final CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final MapFactory MAP_FACTORY = new MapFactoryStub();

    private PersistentVariableCacheFactory _persistentVariableCacheFactory;

    @BeforeEach
    void setUp() {
        _persistentVariableCacheFactory = new PersistentVariableCacheFactoryImpl(COLLECTION_FACTORY,
                MAP_FACTORY);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(PersistentVariableCacheFactory.class.getCanonicalName(),
                _persistentVariableCacheFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_persistentVariableCacheFactory.make());
    }
}
