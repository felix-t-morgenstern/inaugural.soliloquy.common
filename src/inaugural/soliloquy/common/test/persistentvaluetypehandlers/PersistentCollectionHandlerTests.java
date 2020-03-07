package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentCollectionHandler;
import inaugural.soliloquy.common.test.fakes.FakeCollectionFactory;
import inaugural.soliloquy.common.test.fakes.FakeCollection;
import inaugural.soliloquy.common.test.fakes.FakePersistentStringHandler;
import inaugural.soliloquy.common.test.fakes.FakePersistentValuesHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.PersistentValuesHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentCollectionHandlerTests {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER = new FakePersistentValuesHandler();
    private final CollectionFactory COLLECTION_FACTORY = new FakeCollectionFactory();
    private final Integer INTEGER_1 = 123;
    private final Integer INTEGER_2 = 456;
    private final Integer INTEGER_3 = 789;
    private final String VALUES_STRING =
            String.format("{\"typeName\":\"%s\",\"serializedValues\":[\"%d\",\"%d\",\"%d\"]}",
                    Integer.class.getCanonicalName(),
                    INTEGER_1, INTEGER_2, INTEGER_3);

    private PersistentCollectionHandler _persistentCollectionHandler;

    @BeforeEach
    void setUp() {
        _persistentCollectionHandler = new PersistentCollectionHandler(PERSISTENT_VALUES_HANDLER,
                COLLECTION_FACTORY);
    }

    @Test
    void testWrite() {
        Collection<Integer> collection = new FakeCollection<>(0);
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
        Collection<Integer> collection = _persistentCollectionHandler.read(VALUES_STRING);

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
        assertEquals(FakePersistentStringHandler.ARCHETYPE,
                _persistentCollectionHandler.generateArchetype(
                        Collection.class.getCanonicalName() + "<" +
                                String.class.getCanonicalName() + ">").getArchetype());
    }

    @Test
    void testGenerateArchetypeWithInvalidInputs() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCollectionHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCollectionHandler.generateArchetype(""));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCollectionHandler.generateArchetype("Map<java.lang.String>"));

    }
}
