package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.PersistentVariableCacheFactory;
import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.common.test.stubs.MapFactoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.common.factories.IPersistentVariableCacheFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersistentVariableCacheFactoryTests {
    private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final IMapFactory MAP_FACTORY = new MapFactoryStub();

    private IPersistentVariableCacheFactory _persistentVariableCacheFactory;

    @BeforeEach
    void setUp() {
        _persistentVariableCacheFactory = new PersistentVariableCacheFactory(COLLECTION_FACTORY,
                MAP_FACTORY);
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
