package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentVariableCachePersistenceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersistentVariableCachePersistenceHandlerTests {
    private static String _persistentValuesHandlerDataRead;
    public static HashMap<String,Object> _persistentVariableFactoryValuesRead;
    private static HashMap<String,Object> _persistentVariableFactoryValuesWritten;

    private IPersistentValueTypeHandler<IPersistentVariableCache>
            _persistentVariablePersistentCachePersistenceHandler;

    @BeforeEach
    void setUp() {
        // TODO: Set this up properly!
//        _persistentVariablePersistentCachePersistenceHandler =
//                new PersistentVariableCachePersistenceHandler();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(IPersistentValueTypeHandler.class.getCanonicalName() + "<" +
                IPersistentVariableCache.class.getCanonicalName() + ">",
                _persistentVariablePersistentCachePersistenceHandler.getInterfaceName());
    }

    @Test
    void testRead() {
        String jankyInput = "name1\u000fvalue1\u000ename2\u000fvalue2\u000ename3\u000fvalue3";

        IPersistentVariableCache persistentVariableCache =
                _persistentVariablePersistentCachePersistenceHandler.read(jankyInput);

    	// Test whether data and overridePreviousData are passed through to the PersistentValuesHandler
		assertEquals(_persistentValuesHandlerDataRead, jankyInput);

    	// Test whether the output of the PersistentValuesHandler is passed through to the PersistentVariableFactory
		assertEquals(3, _persistentVariableFactoryValuesRead.size());
		assertEquals("value1", _persistentVariableFactoryValuesRead.get("name1"));
		assertEquals("value2", _persistentVariableFactoryValuesRead.get("name2"));
		assertEquals("value3", _persistentVariableFactoryValuesRead.get("name3"));

    	// Test whether the PersistentVariableCache contains the output of the PersistentVariableFactory
		assertEquals(3, persistentVariableCache.size());
		assertEquals("value1", persistentVariableCache.get("name1").getValue());
		assertEquals("value2", persistentVariableCache.get("name2").getValue());
		assertEquals("value3", persistentVariableCache.get("name3").getValue());
    }

    private class PersistentValuesHandlerStub implements IPersistentValuesHandler {

        @Override
        public void addPersistentValueTypeHandler(IPersistentValueTypeHandler<?> persistentValueTypeHandler)
                throws IllegalArgumentException {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removePersistentValueTypeHandler(String persistentValueType) {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> IPersistentValueTypeHandler<T> getPersistentValueTypeHandler(String persistentValueType)
                throws UnsupportedOperationException {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public ICollection<String> persistentValueTypesHandled() {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public void registerPersistentPairHandler(IPersistentValueTypeHandler<IPair> iPersistentValueTypeHandler) {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public void registerPersistentCollectionHandler(IPersistentValueTypeHandler<ICollection> iPersistentValueTypeHandler) {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public void registerPersistentMapHandler(IPersistentValueTypeHandler<IMap> iPersistentValueTypeHandler) {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public String getInterfaceName() {
            // Stub; not implemented
            throw new UnsupportedOperationException();
        }

    }
}
