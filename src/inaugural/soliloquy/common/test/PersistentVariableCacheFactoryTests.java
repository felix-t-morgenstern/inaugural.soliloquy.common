package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.PersistentVariableCacheFactory;
import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IPersistentVariableCacheFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersistentVariableCacheFactoryTests {
    private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();

    private IPersistentVariableCacheFactory _persistentVariableCacheFactory;

    @BeforeEach
    void setUp() {
        _persistentVariableCacheFactory = new PersistentVariableCacheFactory(COLLECTION_FACTORY);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(IPersistentVariableCacheFactory.class.getCanonicalName(),
                _persistentVariableCacheFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        assertNotNull(_persistentVariableCacheFactory.make());
    }
}