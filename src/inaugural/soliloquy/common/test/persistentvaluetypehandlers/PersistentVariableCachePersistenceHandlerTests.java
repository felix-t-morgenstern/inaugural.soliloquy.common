package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentVariableCachePersistenceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersistentVariableCachePersistenceHandlerTests {
    private static String _persistentValuesHandlerDataRead;
    public static HashMap<String,Object> _persistentVariableFactoryValuesRead;
    private static HashMap<String,Object> _persistentVariableFactoryValuesWritten;

    private IPersistentValueTypeHandler<IPersistentVariableCache>
            _persistentVariablePersistentCachePersistenceHandler;

    @BeforeEach
    void setUp() {
        _persistentVariablePersistentCachePersistenceHandler =
                new PersistentVariableCachePersistenceHandler();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(IPersistentValueTypeHandler.class.getCanonicalName() + "<" +
                IPersistentVariableCache.class.getCanonicalName() + ">",
                _persistentVariablePersistentCachePersistenceHandler.getInterfaceName());
    }

    @Test
    void testRead() {
        String jankyInput = "name1,value1;name2,value2;name3,value3";

        IPersistentVariableCache persistentVariableCache =
                _persistentVariablePersistentCachePersistenceHandler.read(jankyInput);

        // TODO: Get this test working properly
        return;

//    	// Test whether data and overridePreviousData are passed through to the PersistentValuesHandler
//		assertEquals(_persistentValuesHandlerDataRead, jankyInput);
//		assertTrue(_persistentValuesHandlerBooleanRead);
//
//    	// Test whether the output of the PersistentValuesHandler is passed through to the PersistentVariableFactory
//		assertEquals(3, _persistentVariableFactoryValuesRead.size());
//		assertEquals("value1", _persistentVariableFactoryValuesRead.get("name1"));
//		assertEquals("value2", _persistentVariableFactoryValuesRead.get("name2"));
//		assertEquals("value3", _persistentVariableFactoryValuesRead.get("name3"));
//
//    	// Test whether the PersistentVariableCache contains the output of the PersistentVariableFactory
//		assertEquals(3, persistentVariableCache.size());
//		assertEquals("value1", persistentVariableCache.get("name1").getValue());
//		assertEquals("value2", persistentVariableCache.get("name2").getValue());
//		assertEquals("value3", persistentVariableCache.get("name3").getValue());
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

        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public void readValues(String valuesString,
                               IAction<IPersistentValueToWrite> valueProcessing) {
            _persistentValuesHandlerDataRead = valuesString;

            String[] nameValuePairs = valuesString.split(";");
            for(String nameValuePair : nameValuePairs) {
                String[] nameAndValue = nameValuePair.split(",");
                IPersistentValueToWrite persistentValueToWrite =
                        new PersistentValueToWrite(null, nameAndValue[0], nameAndValue[1]);
                valueProcessing.run(persistentValueToWrite);
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public String writeValues(ICollection<IPersistentValueToWrite> persistentValuesToProcess) {
            StringBuilder result = new StringBuilder();
            for (IPersistentValueToWrite persistentValueToWrite : persistentValuesToProcess) {
                _persistentVariableFactoryValuesWritten.put(persistentValueToWrite.name(), persistentValueToWrite.value());
                if (!result.toString().equals("")) {
                    result.append(";");
                }
                result.append(persistentValueToWrite.name()).append(",").append(persistentValueToWrite.value());
            }
            return result.toString();
        }

        @Override
        public IPersistentValueToRead makePersistentValueToRead(String typeName, String name, String value) {
            // Stub method; not implemented
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> IPersistentValueToWrite<T> makePersistentValueToWrite(String name, T value) {
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

    private class PersistentValueToWrite<T> implements IPersistentValueToWrite<T> {
        private final String TYPE_NAME;
        private final String NAME;
        private final T VALUE;

        private PersistentValueToWrite(String typeName, String name, T value) {
            TYPE_NAME = typeName;
            NAME = name;
            VALUE = value;
        }

        @Override
        public T getArchetype() {
            // Stub method
            throw new UnsupportedOperationException();
        }

        @Override
        public String getInterfaceName() {
            // Stub method
            throw new UnsupportedOperationException();
        }

        @Override
        public String typeName() {
            return TYPE_NAME;
        }

        @Override
        public String name() {
            return NAME;
        }

        @Override
        public T value() {
            return VALUE;
        }

        @Override
        public String getUnparameterizedInterfaceName() {
            // Stub method; unimplemented
            throw new UnsupportedOperationException();
        }

    }
}
