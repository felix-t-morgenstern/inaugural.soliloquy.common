package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentPairHandler;
import inaugural.soliloquy.common.test.fakes.FakePairFactory;
import inaugural.soliloquy.common.test.fakes.FakePersistentValuesHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.PairFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.infrastructure.PersistentValuesHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersistentPairHandlerTests {
    private final PairFactory PAIR_FACTORY = new FakePairFactory();
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new FakePersistentValuesHandler();
    private final String VALUES_STRING =
            "{\"valueType1\":\"java.lang.String\",\"serializedValue1\":\"String\"," +
                    "\"valueType2\":\"java.lang.Integer\",\"serializedValue2\":\"123\"}";

    private PersistentPairHandler _persistentPairHandler;

    @BeforeEach
    void setUp() {
        _persistentPairHandler =
                new PersistentPairHandler(PERSISTENT_VALUES_HANDLER, PAIR_FACTORY);
    }

    @Test
    void testGetInterfaceName() {
        assertThrows(UnsupportedOperationException.class,
                () -> _persistentPairHandler.getInterfaceName());
    }

    @Test
    void testGetUnparameterizedInterfaceName() {
        assertThrows(UnsupportedOperationException.class,
                () -> _persistentPairHandler.getUnparameterizedInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertThrows(UnsupportedOperationException.class,
                () -> _persistentPairHandler.getArchetype());
    }

    @Test
    void testWrite() {
        Pair<String,Integer> pair = PAIR_FACTORY.make("String",123);
        assertEquals(VALUES_STRING, _persistentPairHandler.write(pair));
    }

    @Test
    void testWriteNull() {
        assertThrows(IllegalArgumentException.class, () -> _persistentPairHandler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRead() {
        Pair<String,Integer> pair = _persistentPairHandler.read(VALUES_STRING);
        assertEquals("String", pair.getItem1());
        assertEquals((Integer) 123, pair.getItem2());
    }

    @Test
    void testReadWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> _persistentPairHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> _persistentPairHandler.read(""));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGenerateArchetype() {
        final String valueType = Pair.class.getCanonicalName() + "<" +
                String.class.getCanonicalName() + "," + Collection.class.getCanonicalName() +
                "<" + Integer.class.getCanonicalName() + ">>";

        Pair<String, Collection<Integer>> archetype =
                _persistentPairHandler.generateArchetype(valueType);

        assertNotNull(archetype);
        assertNotNull(archetype.getFirstArchetype());
        assertNotNull(archetype.getSecondArchetype());
        assertNotNull(((Collection)archetype.getSecondArchetype()).getArchetype());
    }

    @Test
    void testGenerateArchetypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentPairHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentPairHandler.generateArchetype(""));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentPairHandler.generateArchetype(Pair.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentPairHandler.generateArchetype(Pair.class.getCanonicalName() +
                        "<"));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentPairHandler.generateArchetype(Pair.class.getCanonicalName() +
                        "<>"));
    }
}
