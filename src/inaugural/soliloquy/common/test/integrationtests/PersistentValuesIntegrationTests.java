package inaugural.soliloquy.common.test.integrationtests;

import java.util.ArrayList;
import java.util.List;

import inaugural.soliloquy.common.persistentvaluetypehandlers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.CollectionFactory;
import inaugural.soliloquy.common.GenericParamsSetFactory;
import inaugural.soliloquy.common.MapFactory;
import inaugural.soliloquy.common.PairFactory;
import inaugural.soliloquy.common.PersistentValuesHandler;
import inaugural.soliloquy.common.SettingFactory;
import soliloquy.common.specs.IAction;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPairFactory;
import soliloquy.common.specs.IPersistentValueToWrite;
import soliloquy.common.specs.IPersistentValueTypeHandler;
import soliloquy.common.specs.IPersistentValuesHandler;
import soliloquy.common.specs.ISoliloquyClass;
import soliloquy.game.primary.specs.IGame;
import soliloquy.logger.specs.ILogger;

import static org.junit.jupiter.api.Assertions.*;

class PersistentValuesIntegrationTests {
	private IPersistentValuesHandler _persistentValuesHandler;
	
	private ICollectionFactory _collectionFactory;

	private static List<IPair<IPersistentValueToWrite<?>, Boolean>> _readValues;
	
    @BeforeEach
	void setUp() {
		_collectionFactory = new CollectionFactory();

    	_persistentValuesHandler = new PersistentValuesHandler();

		IPairFactory _pairFactory = new PairFactory();
		IMapFactory _mapFactory = new MapFactory(_pairFactory);
    	new GenericParamsSetFactory(_persistentValuesHandler, _mapFactory);
    	new SettingFactory();
    	
    	IPersistentValueTypeHandler<Boolean> persistentBooleanHandler = new PersistentBooleanHandler();
    	IPersistentValueTypeHandler<Integer> persistentIntegerHandler = new PersistentIntegerHandler();
    	IPersistentValueTypeHandler<String> persistentStringHandler = new PersistentStringHandler();
		IPersistentValueTypeHandler<ICollection> persistentCollectionHandler = new PersistentCollectionHandler(_persistentValuesHandler, _collectionFactory);
    	
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentBooleanHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentIntegerHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentStringHandler);
    	_persistentValuesHandler.registerPersistentCollectionHandler(persistentCollectionHandler);
    	
    	_readValues = new ArrayList<>();
    }
    
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
	void testWriteValues() {
    	String expectedOutput = "[{\"typeName\":\"java.lang.Boolean\",\"name\":\"bool1\",\"value\":\"true\"},{\"typeName\":\"java.lang.Boolean\",\"name\":\"bool2\",\"value\":\"false\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"integers1\",\"value\":\"java.lang.Integer\\u001d0\\u001d1\\u001e2\\u001e3\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"integers2\",\"value\":\"java.lang.Integer\\u001d0\\u001d4\\u001e5\\u001e6\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int1\",\"value\":\"123\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int2\",\"value\":\"456\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int3\",\"value\":\"789\"},{\"typeName\":\"java.lang.String\",\"name\":\"string1\",\"value\":\"String1\"},{\"typeName\":\"java.lang.String\",\"name\":\"string2\",\"value\":\"String2\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"strings1\",\"value\":\"java.lang.String\\u001d\\u001dstring1-1\\u001estring1-2\\u001estring1-3\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"strings2\",\"value\":\"java.lang.String\\u001d\\u001dstring2-1\\u001estring2-2\\u001estring2-3\"}]";
    	
    	IPersistentValueToWrite persistentValueToWriteArchetype = new PersistentValueToWrite(null, null);
    	ICollection<IPersistentValueToWrite<?>> persistentValuesToWrite =
    			_collectionFactory.make(persistentValueToWriteArchetype);
    	
    	// Adding boolean persistent values
    	IPersistentValueToWrite bool1ToWrite = new PersistentValueToWrite("bool1", true);
    	persistentValuesToWrite.add(bool1ToWrite);

    	IPersistentValueToWrite bool2ToWrite = new PersistentValueToWrite("bool2", false);
    	persistentValuesToWrite.add(bool2ToWrite);
    	
    	// Adding integers persistent values
    	ICollection<Integer> integers1 = _collectionFactory.make(0);
    	integers1.add(1);
    	integers1.add(2);
    	integers1.add(3);
    	IPersistentValueToWrite integers1ToWrite = new PersistentValueToWrite("integers1", integers1);
    	persistentValuesToWrite.add(integers1ToWrite);
    	
    	ICollection<Integer> integers2 = _collectionFactory.make(0);
    	integers2.add(4);
    	integers2.add(5);
    	integers2.add(6);
    	IPersistentValueToWrite integers2ToWrite = new PersistentValueToWrite("integers2", integers2);
    	persistentValuesToWrite.add(integers2ToWrite);
    	
    	// Adding integer persistent values
    	Integer int1 = 123;
    	IPersistentValueToWrite int1ToWrite = new PersistentValueToWrite("int1", int1);
    	persistentValuesToWrite.add(int1ToWrite);
    	
    	Integer int2 = 456;
    	IPersistentValueToWrite int2ToWrite = new PersistentValueToWrite("int2", int2);
    	persistentValuesToWrite.add(int2ToWrite);
    	
    	Integer int3 = 789;
    	IPersistentValueToWrite int3ToWrite = new PersistentValueToWrite("int3", int3);
    	persistentValuesToWrite.add(int3ToWrite);
    	
    	// Adding String persistent values
    	String string1 = "String1";
    	IPersistentValueToWrite string1ToWrite = new PersistentValueToWrite("string1", string1);
    	persistentValuesToWrite.add(string1ToWrite);

    	String string2 = "String2";
    	IPersistentValueToWrite string2ToWrite = new PersistentValueToWrite("string2", string2);
    	persistentValuesToWrite.add(string2ToWrite);
    	
    	// Adding Strings persistent values
    	ICollection<String> strings1 = _collectionFactory.make(new String[] {"string1-1", "string1-2", "string1-3"}, "");
    	IPersistentValueToWrite strings1ToWrite = new PersistentValueToWrite("strings1", strings1);
    	persistentValuesToWrite.add(strings1ToWrite);

    	ICollection<String> strings2 = _collectionFactory.make(new String[] {"string2-1", "string2-2", "string2-3"}, "");
    	IPersistentValueToWrite strings2ToWrite = new PersistentValueToWrite("strings2", strings2);
    	persistentValuesToWrite.add(strings2ToWrite);
    	
    	String writtenValue = _persistentValuesHandler.writeValues(persistentValuesToWrite);
    	
    	assertEquals(expectedOutput, writtenValue);
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
	void testReadValues() throws Exception {
    	String valuesToRead = "[{\"typeName\":\"java.lang.Boolean\",\"name\":\"bool1\",\"value\":\"true\"},{\"typeName\":\"java.lang.Boolean\",\"name\":\"bool2\",\"value\":\"false\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"integers1\",\"value\":\"java.lang.Integer\\u001d0\\u001d1\\u001e2\\u001e3\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"integers2\",\"value\":\"java.lang.Integer\\u001d0\\u001d4\\u001e5\\u001e6\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int1\",\"value\":\"123\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int2\",\"value\":\"456\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int3\",\"value\":\"789\"},{\"typeName\":\"java.lang.String\",\"name\":\"string1\",\"value\":\"String1\"},{\"typeName\":\"java.lang.String\",\"name\":\"string2\",\"value\":\"String2\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"strings1\",\"value\":\"java.lang.String\\u001d\\u001dstring1-1\\u001estring1-2\\u001estring1-3\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"strings2\",\"value\":\"java.lang.String\\u001d\\u001dstring2-1\\u001estring2-2\\u001estring2-3\"}]";
    	
    	_persistentValuesHandler.readValues(valuesToRead, new ReadValuesAction(), true);

		assertEquals(11, _readValues.size());
    	
    	for (IPair<IPersistentValueToWrite<?>, Boolean> readValuePair : _readValues)
    	{
    		assertTrue(readValuePair.getItem2());
    		IPersistentValueToWrite readValue = readValuePair.getItem1();
    		switch(readValue.name())
    		{
    		case "bool1":
				assertEquals(true, readValue.value());
    			break;
    		case "bool2":
				assertEquals(false, readValue.value());
    			break;
    		case "integers1":
    			ICollection<Integer> integers1 = (ICollection<Integer>) readValue.value();
				assertEquals(3, integers1.size());
				assertEquals(1, (int) integers1.get(0));
				assertEquals(2, (int) integers1.get(1));
				assertEquals(3, (int) integers1.get(2));
				assertNotNull(integers1.getArchetype());
    			break;
    		case "integers2":
    			ICollection<Integer> integers2 = (ICollection<Integer>) readValue.value();
				assertEquals(3, integers2.size());
				assertEquals(4, (int) integers2.get(0));
				assertEquals(5, (int) integers2.get(1));
				assertEquals(6, (int) integers2.get(2));
				assertNotNull(integers2.getArchetype());
    			break;
    		case "int1":
				assertEquals(123, (int) (Integer) readValue.value());
    			break;
    		case "int2":
				assertEquals(456, (int) (Integer) readValue.value());
    			break;
    		case "int3":
				assertEquals(789, (int) (Integer) readValue.value());
    			break;
    		case "string1":
				assertEquals("String1", readValue.value());
    			break;
    		case "string2":
				assertEquals("String2", readValue.value());
    			break;
    		case "strings1":
    			ICollection<String> strings1 = (ICollection<String>) readValue.value();
				assertEquals(3, strings1.size());
				assertEquals("string1-1", strings1.get(0));
				assertEquals("string1-2", strings1.get(1));
				assertEquals("string1-3", strings1.get(2));
    			assertNotNull(strings1.getArchetype());
    			break;
    		case "strings2":
    			ICollection<String> strings2 = (ICollection<String>) readValue.value();
				assertEquals(3, strings2.size());
				assertEquals("string2-1", strings2.get(0));
				assertEquals("string2-2", strings2.get(1));
				assertEquals("string2-3", strings2.get(2));
				assertNotNull(strings2.getArchetype());
    			break;
    		default:
				throw new Exception();
    		}
    	}
    }
    
    private class PersistentValueToWrite<T> implements IPersistentValueToWrite<T> {
    	private final String NAME;
    	private final T VALUE;
    	
    	private PersistentValueToWrite(String name, T value) {
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
			return VALUE instanceof ISoliloquyClass ? 
				((ISoliloquyClass) VALUE).getInterfaceName() 
					: VALUE.getClass().getCanonicalName();
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
			// Stub method
			throw new UnsupportedOperationException();
		}
    	
    }
    
    private class ReadValuesAction implements IAction<IPair<IPersistentValueToWrite<?>, Boolean>> {
		@Override
		public String id() throws IllegalStateException {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public IPair<IPersistentValueToWrite<?>, Boolean> getArchetype() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public String getInterfaceName() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public void run(IPair<IPersistentValueToWrite<?>, Boolean> input) throws IllegalArgumentException {
			_readValues.add(input);
		}

		@Override
		public String getUnparameterizedInterfaceName() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public IGame game() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public ILogger logger() {
			// Stub method
			throw new UnsupportedOperationException();
		}
    }
}
