package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentPairHandler;
import inaugural.soliloquy.common.test.stubs.PairFactoryStub;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPairFactory;
import soliloquy.common.specs.IPersistentValueTypeHandler;
import soliloquy.common.specs.IPersistentValuesHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersistentPairHandlerTests {
    private final IPairFactory PAIR_FACTORY = new PairFactoryStub();
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER = new PersistentValuesHandlerStub();
    private final String VALUES_STRING = "java.lang.String\u008CString\u008Bjava.lang.Integer\u008C123";

    private IPersistentValueTypeHandler<IPair> _persistentPairHandler;

    @BeforeEach
    void setUp() {
        _persistentPairHandler = new PersistentPairHandler(PAIR_FACTORY, PERSISTENT_VALUES_HANDLER);
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
        assertThrows(IllegalArgumentException.class, () -> _persistentPairHandler.read(
                "java.lang.String\u008CString"));
        assertThrows(IllegalArgumentException.class, () -> _persistentPairHandler.read(
                "java.lang.String\u008CString\u008Bjava.lang.Integer\u008C123\u008Bjava.lang.String\u008CString"));
        assertThrows(IllegalArgumentException.class, () -> _persistentPairHandler.read(
                "java.lang.String\u008CString\u008Bjava.lang.Integer\u008C123\u008C456"));
        assertThrows(IllegalArgumentException.class, () -> _persistentPairHandler.read(
                "java.lang.String\u008CString\u008Bjava.lang.Integer"));
    }
}
