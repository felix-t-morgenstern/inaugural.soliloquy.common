package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentCollectionHandler;
import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.common.test.stubs.CollectionStub;
import inaugural.soliloquy.common.test.stubs.PersistentStringHandlerStub;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.*;

import static org.junit.jupiter.api.Assertions.*;

class PersistentCollectionHandlerTests {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER = new PersistentValuesHandlerStub();
    private final ICollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private final Integer ARCHETYPE = 123123123;
    private final Integer INTEGER_1 = 123;
    private final Integer INTEGER_2 = 456;
    private final Integer INTEGER_3 = 789;
    private final String VALUES_STRING =
            String.format("%s\u0091%d\u0091%d\u0092%d\u0092%d", Integer.class.getCanonicalName(),
                    ARCHETYPE, INTEGER_1, INTEGER_2, INTEGER_3);

    private IPersistentCollectionHandler _persistentCollectionHandler;

    @BeforeEach
    void setUp() {
        _persistentCollectionHandler = new PersistentCollectionHandler(PERSISTENT_VALUES_HANDLER,
                COLLECTION_FACTORY);
    }

    @Test
    void testWrite() {
        ICollection<Integer> collection = new CollectionStub<>(ARCHETYPE);
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

        assertEquals(3, collection.size());
        assertTrue(collection.contains(INTEGER_1));
        assertTrue(collection.contains(INTEGER_2));
        assertTrue(collection.contains(INTEGER_3));
        assertEquals(ARCHETYPE, collection.getArchetype());
    }

    @Test
    void testReadInvalidValues() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCollectionHandler.read(String.format(
                        "%s\u001d%d\u001d", Integer.class.getCanonicalName(), ARCHETYPE)));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCollectionHandler.read(VALUES_STRING + "\u001d123123"));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentCollectionHandler.read(String.format(
                        "%s\u001d%d\u001d%s\u001e%d\u001e%d", Integer.class.getCanonicalName(),
                        ARCHETYPE, "INVALID", INTEGER_2, INTEGER_3)));
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
