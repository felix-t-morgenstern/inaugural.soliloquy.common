package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentPairHandler;
import inaugural.soliloquy.common.test.stubs.PairFactoryStub;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.IPersistentPairHandler;
import soliloquy.specs.common.entities.IPersistentValuesHandler;
import soliloquy.specs.common.factories.IPairFactory;
import soliloquy.specs.common.valueobjects.ICollection;
import soliloquy.specs.common.valueobjects.IPair;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PersistentPairHandlerTests {
    private final IPairFactory PAIR_FACTORY = new PairFactoryStub();
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new PersistentValuesHandlerStub();
    private final String VALUES_STRING =
            "{\"valueType1\":\"java.lang.String\",\"valueString1\":\"String\"," +
                    "\"valueType2\":\"java.lang.Integer\",\"valueString2\":\"123\"}";

    private IPersistentPairHandler _persistentPairHandler;

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
        IPair<String,Integer> pair = PAIR_FACTORY.make("String",123);
        assertEquals(VALUES_STRING, _persistentPairHandler.write(pair));
    }

    @Test
    void testWriteNull() {
        assertThrows(IllegalArgumentException.class, () -> _persistentPairHandler.write(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRead() {
        IPair<String,Integer> pair = _persistentPairHandler.read(VALUES_STRING);
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
        final String valueType = IPair.class.getCanonicalName() + "<" +
                String.class.getCanonicalName() + "," + ICollection.class.getCanonicalName() +
                "<" + Integer.class.getCanonicalName() + ">>";

        IPair<String, ICollection<Integer>> archetype =
                _persistentPairHandler.generateArchetype(valueType);

        assertNotNull(archetype);
        assertNotNull(archetype.getFirstArchetype());
        assertNotNull(archetype.getSecondArchetype());
        assertNotNull(((ICollection)archetype.getSecondArchetype()).getArchetype());
    }

    @Test
    void testGenerateArchetypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentPairHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentPairHandler.generateArchetype(""));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentPairHandler.generateArchetype(IPair.class.getCanonicalName()));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentPairHandler.generateArchetype(IPair.class.getCanonicalName() +
                        "<"));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentPairHandler.generateArchetype(IPair.class.getCanonicalName() +
                        "<>"));
    }
}
