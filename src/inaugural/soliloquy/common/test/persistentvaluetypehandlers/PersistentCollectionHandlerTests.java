package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentCollectionHandler;
import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.common.test.stubs.CollectionStub;
import inaugural.soliloquy.common.test.stubs.PersistentStringHandlerStub;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ICollectionFactory;
import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IPersistentCollectionHandler;
import soliloquy.specs.common.infrastructure.IPersistentValuesHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentCollectionHandlerTests {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER = new PersistentValuesHandlerStub();
    private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final Integer INTEGER_1 = 123;
    private final Integer INTEGER_2 = 456;
    private final Integer INTEGER_3 = 789;
    private final String VALUES_STRING =
            String.format("{\"typeName\":\"%s\",\"serializedValues\":[\"%d\",\"%d\",\"%d\"]}",
                    Integer.class.getCanonicalName(),
                    INTEGER_1, INTEGER_2, INTEGER_3);

    private IPersistentCollectionHandler _persistentCollectionHandler;

    @BeforeEach
    void setUp() {
        _persistentCollectionHandler = new PersistentCollectionHandler(PERSISTENT_VALUES_HANDLER,
                COLLECTION_FACTORY);
    }

    @Test
    void testWrite() {
        ICollection<Integer> collection = new CollectionStub<>(0);
        collection.add(INTEGER_1);
        collection.add(INTEGER_2);
        collection.add(INTEGER_3);

        assertEquals(VALUES_STRING, _persistentCollectionHandler.write(collection));
    }

    @Test
    void testWriteNull() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCollectionHandler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRead() {
        ICollection<Integer> collection = _persistentCollectionHandler.read(VALUES_STRING);

        assertNotNull(collection);
        assertNotNull(collection.getArchetype());
        assertEquals(3, collection.size());
        assertTrue(collection.contains(INTEGER_1));
        assertTrue(collection.contains(INTEGER_2));
        assertTrue(collection.contains(INTEGER_3));
    }

    @Test
    void testReadInvalidValues() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCollectionHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCollectionHandler.read(""));
    }

    @Test
    void testGetInterfaceName() {
        assertThrows(UnsupportedOperationException.class,
                () -> _persistentCollectionHandler.getInterfaceName());
    }

    @Test
    void testGenerateArchetype() {
        assertEquals(PersistentStringHandlerStub.ARCHETYPE,
                _persistentCollectionHandler.generateArchetype(
                        ICollection.class.getCanonicalName() + "<" +
                                String.class.getCanonicalName() + ">").getArchetype());
    }

    @Test
    void testGenerateArchetypeWithInvalidInputs() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCollectionHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCollectionHandler.generateArchetype(""));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCollectionHandler.generateArchetype("IMap<java.lang.String>"));

    }
}
