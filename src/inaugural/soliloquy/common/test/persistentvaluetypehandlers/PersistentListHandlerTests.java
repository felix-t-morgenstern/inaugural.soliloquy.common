package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentListHandler;
import inaugural.soliloquy.common.test.fakes.FakeList;
import inaugural.soliloquy.common.test.fakes.FakeListFactory;
import inaugural.soliloquy.common.test.fakes.FakePersistentStringHandler;
import inaugural.soliloquy.common.test.fakes.FakePersistentValuesHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.PersistentValuesHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentListHandlerTests {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER = new FakePersistentValuesHandler();
    private final ListFactory LIST_FACTORY = new FakeListFactory();
    private final Integer INTEGER_1 = 123;
    private final Integer INTEGER_2 = 456;
    private final Integer INTEGER_3 = 789;
    private final String VALUES_STRING =
            String.format("{\"typeName\":\"%s\",\"serializedValues\":[\"%d\",\"%d\",\"%d\"]}",
                    Integer.class.getCanonicalName(),
                    INTEGER_1, INTEGER_2, INTEGER_3);

    private PersistentListHandler _persistentListHandler;

    @BeforeEach
    void setUp() {
        _persistentListHandler = new PersistentListHandler(PERSISTENT_VALUES_HANDLER,
                LIST_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentListHandler(null, LIST_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentListHandler(PERSISTENT_VALUES_HANDLER, null));
    }

    @Test
    void testWrite() {
        List<Integer> collection = new FakeList<>(0);
        collection.add(INTEGER_1);
        collection.add(INTEGER_2);
        collection.add(INTEGER_3);

        assertEquals(VALUES_STRING, _persistentListHandler.write(collection));
    }

    @Test
    void testWriteNull() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentListHandler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRead() {
        List<Integer> collection = _persistentListHandler.read(VALUES_STRING);

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
                () -> _persistentListHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentListHandler.read(""));
    }

    @Test
    void testGetInterfaceName() {
        assertThrows(UnsupportedOperationException.class,
                () -> _persistentListHandler.getInterfaceName());
    }

    @Test
    void testGenerateArchetype() {
        assertEquals(FakePersistentStringHandler.ARCHETYPE,
                _persistentListHandler.generateArchetype(
                        List.class.getCanonicalName() + "<" +
                                String.class.getCanonicalName() + ">").getArchetype());
    }

    @Test
    void testGenerateArchetypeWithInvalidInputs() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentListHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentListHandler.generateArchetype(""));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentListHandler.generateArchetype("Map<java.lang.String>"));

    }
}
