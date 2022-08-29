package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PairHandler;
import inaugural.soliloquy.common.test.fakes.FakePersistentValuesHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.Pair;

import static org.junit.jupiter.api.Assertions.*;

class PairHandlerTests {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new FakePersistentValuesHandler();
    private final String VALUES_STRING =
            "{\"valueType1\":\"java.lang.String\",\"serializedValue1\":\"String\"," +
                    "\"valueType2\":\"java.lang.Integer\",\"serializedValue2\":\"123\"}";

    private PairHandler _pairHandler;

    @BeforeEach
    void setUp() {
        _pairHandler = new PairHandler(PERSISTENT_VALUES_HANDLER);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new PairHandler(null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Pair.class.getCanonicalName() + ">",
                _pairHandler.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_pairHandler.getArchetype());
        assertEquals(Pair.class.getCanonicalName(),
                _pairHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testWrite() {
        Pair<String, Integer> pair = new Pair<>("String", 123);
        assertEquals(VALUES_STRING, _pairHandler.write(pair));
    }

    @Test
    void testWriteNull() {
        assertThrows(IllegalArgumentException.class, () -> _pairHandler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRead() {
        Pair<String, Integer> pair = _pairHandler.read(VALUES_STRING);
        assertEquals("String", pair.getItem1());
        assertEquals((Integer) 123, pair.getItem2());
    }

    @Test
    void testReadWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> _pairHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> _pairHandler.read(""));
    }

    @Test
    void testGenerateArchetype() {
        //noinspection unchecked
        Pair<Integer, String> generatedArchetype = _pairHandler.generateArchetype(
                Integer.class.getCanonicalName(), String.class.getCanonicalName());

        assertNotNull(generatedArchetype);
        assertNotNull(generatedArchetype.getFirstArchetype());
        assertNotNull(generatedArchetype.getSecondArchetype());
    }

    @Test
    void testGenerateArchetypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _pairHandler.generateArchetype(null, String.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                _pairHandler.generateArchetype("", String.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class, () ->
                _pairHandler.generateArchetype(String.class.getCanonicalName(), null));
        assertThrows(IllegalArgumentException.class, () ->
                _pairHandler.generateArchetype(String.class.getCanonicalName(), ""));
    }
}
