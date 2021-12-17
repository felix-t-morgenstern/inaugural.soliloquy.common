package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.ListHandler;
import inaugural.soliloquy.common.test.fakes.FakeList;
import inaugural.soliloquy.common.test.fakes.FakeListFactory;
import inaugural.soliloquy.common.test.fakes.FakeListHandler;
import inaugural.soliloquy.common.test.fakes.FakePersistentValuesHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.persistence.TypeHandler;

import static org.junit.jupiter.api.Assertions.*;

class ListHandlerTests {
    private final FakePersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new FakePersistentValuesHandler();
    private final ListFactory LIST_FACTORY = new FakeListFactory();
    private final Integer INTEGER_1 = 123;
    private final Integer INTEGER_2 = 456;
    private final Integer INTEGER_3 = 789;
    private final String VALUES_STRING =
            String.format("{\"typeName\":\"%s\",\"serializedValues\":[\"%d\",\"%d\",\"%d\"]}",
                    Integer.class.getCanonicalName(),
                    INTEGER_1, INTEGER_2, INTEGER_3);

    private ListHandler _listHandler;

    @BeforeEach
    void setUp() {
        _listHandler = new ListHandler(PERSISTENT_VALUES_HANDLER, LIST_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new ListHandler(null, LIST_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new ListHandler(PERSISTENT_VALUES_HANDLER, null));
    }

    @Test
    void testWrite() {
        List<Integer> collection = new FakeList<>(0);
        collection.add(INTEGER_1);
        collection.add(INTEGER_2);
        collection.add(INTEGER_3);

        assertEquals(VALUES_STRING, _listHandler.write(collection));
    }

    @Test
    void testWriteNull() {
        assertThrows(IllegalArgumentException.class,
                () -> _listHandler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRead() {
        List<Integer> collection = _listHandler.read(VALUES_STRING);

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
                () -> _listHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> _listHandler.read(""));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                List.class.getCanonicalName() + ">",
                _listHandler.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_listHandler.getArchetype());
        assertEquals(List.class.getCanonicalName(),
                _listHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testGenerateArchetype() {
        //noinspection unchecked
        List<Integer> generatedArchetype = _listHandler.generateArchetype(
                Integer.class.getCanonicalName());

        assertNotNull(generatedArchetype);
        assertNotNull(generatedArchetype.getArchetype());
    }

    @Test
    void testGenerateArchetypeWithInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () ->
                _listHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class, () ->
                _listHandler.generateArchetype(""));
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        List.class.getCanonicalName() + ">").hashCode(),
                _listHandler.hashCode());
    }

    @Test
    void testEquals() {
        //noinspection rawtypes
        TypeHandler<List> equalHandler =
                new ListHandler(PERSISTENT_VALUES_HANDLER, LIST_FACTORY);
        //noinspection rawtypes
        TypeHandler<List> unequalHandler =
                new FakeListHandler();

        assertEquals(_listHandler, equalHandler);
        assertNotEquals(_listHandler, unequalHandler);
        assertNotEquals(null, _listHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        List.class.getCanonicalName() + ">",
                _listHandler.toString());
    }
}
