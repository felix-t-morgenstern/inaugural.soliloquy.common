package inaugural.soliloquy.common.test.integrationtests;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

public class PersistentValuesIntegrationTests {
	private IPersistentValuesHandler _persistentValuesHandler;
	
	private ICollectionFactory _collectionFactory;
	private IPairFactory _pairFactory;
	private IMapFactory _mapFactory;
	
	private static List<IPair<IPersistentValueToWrite<?>, Boolean>> _readValues;
	
    @BeforeEach
    protected void setUp() throws Exception {
		_collectionFactory = new CollectionFactory();

    	_persistentValuesHandler = new PersistentValuesHandler();

    	_pairFactory = new PairFactory();
    	_mapFactory = new MapFactory(_pairFactory);
    	new GenericParamsSetFactory(_persistentValuesHandler, _mapFactory);
    	new SettingFactory();
    	
    	IPersistentValueTypeHandler<Boolean> persistentBooleanHandler = new PersistentBooleanHandler();
    	IPersistentValueTypeHandler<Integer> persistentIntegerHandler = new PersistentIntegerHandler();
    	IPersistentValueTypeHandler<ICollection<Integer>> persistentIntegersHandler = new PersistentIntegersHandler(_collectionFactory);
    	IPersistentValueTypeHandler<String> persistentStringHandler = new PersistentStringHandler();
    	IPersistentValueTypeHandler<ICollection<String>> persistentStringsHandler = new PersistentStringsHandler(_collectionFactory);
    	
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentBooleanHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentIntegerHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentIntegersHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentStringHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentStringsHandler);
    	
    	_readValues = new ArrayList<IPair<IPersistentValueToWrite<?>, Boolean>>();
    }
    
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void testWriteValues() {
    	String expectedOutput = "[{\"typeName\":\"java.lang.Boolean\",\"name\":\"bool1\",\"value\":\"true\"},{\"typeName\":\"java.lang.Boolean\",\"name\":\"bool2\",\"value\":\"false\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"integers1\",\"value\":\"1,2,3\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"integers2\",\"value\":\"4,5,6\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int1\",\"value\":\"123\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int2\",\"value\":\"456\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int3\",\"value\":\"789\"},{\"typeName\":\"java.lang.String\",\"name\":\"string1\",\"value\":\"String1\"},{\"typeName\":\"java.lang.String\",\"name\":\"string2\",\"value\":\"String2\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"strings1\",\"value\":\"string1-1\\u001fstring1-2\\u001fstring1-3\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"strings2\",\"value\":\"string2-1\\u001fstring2-2\\u001fstring2-3\"}]";
    	
    	IPersistentValueToWrite persistentValueToWriteArchetype = new PersistentValueToWrite(null, null);
    	ICollection<IPersistentValueToWrite<?>> persistentValuesToWrite = 
    			_collectionFactory.make(persistentValueToWriteArchetype);
    	
    	// Adding boolean persistent values
    	Boolean bool1 = true;
    	IPersistentValueToWrite bool1ToWrite = new PersistentValueToWrite("bool1", bool1);
    	persistentValuesToWrite.add(bool1ToWrite);
    	
    	Boolean bool2 = false;
    	IPersistentValueToWrite bool2ToWrite = new PersistentValueToWrite("bool2", bool2);
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
    	
    	assertTrue(expectedOutput.equals(writtenValue));
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void testReadValues() {
    	String valuesToRead = "[{\"typeName\":\"java.lang.Boolean\",\"name\":\"bool1\",\"value\":\"true\"},{\"typeName\":\"java.lang.Boolean\",\"name\":\"bool2\",\"value\":\"false\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"integers1\",\"value\":\"1,2,3\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"integers2\",\"value\":\"4,5,6\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int1\",\"value\":\"123\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int2\",\"value\":\"456\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int3\",\"value\":\"789\"},{\"typeName\":\"java.lang.String\",\"name\":\"string1\",\"value\":\"String1\"},{\"typeName\":\"java.lang.String\",\"name\":\"string2\",\"value\":\"String2\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"strings1\",\"value\":\"string1-1\\u001fstring1-2\\u001fstring1-3\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"strings2\",\"value\":\"string2-1\\u001fstring2-2\\u001fstring2-3\"}]";
    	
    	_persistentValuesHandler.readValues(valuesToRead, new ReadValuesAction(), true);
    	
    	assertTrue(_readValues.size() == 11);
    	
    	for (IPair<IPersistentValueToWrite<?>, Boolean> readValuePair : _readValues)
    	{
    		assertTrue(readValuePair.getItem2());
    		IPersistentValueToWrite readValue = readValuePair.getItem1();
    		switch(readValue.name())
    		{
    		case "bool1":
    			assertTrue((Boolean)readValue.value() == true);
    			break;
    		case "bool2":
    			assertTrue((Boolean)readValue.value() == false);
    			break;
    		case "integers1":
    			ICollection<Integer> integers1 = (ICollection<Integer>) readValue.value();
    			assertTrue(integers1.size() == 3);
    			assertTrue(integers1.get(0) == 1);
    			assertTrue(integers1.get(1) == 2);
    			assertTrue(integers1.get(2) == 3);
    			break;
    		case "integers2":
    			ICollection<Integer> integers2 = (ICollection<Integer>) readValue.value();
    			assertTrue(integers2.size() == 3);
    			assertTrue(integers2.get(0) == 4);
    			assertTrue(integers2.get(1) == 5);
    			assertTrue(integers2.get(2) == 6);
    			break;
    		case "int1":
    			assertTrue((Integer)readValue.value() == 123);
    			break;
    		case "int2":
    			assertTrue((Integer)readValue.value() == 456);
    			break;
    		case "int3":
    			assertTrue((Integer)readValue.value() == 789);
    			break;
    		case "string1":
    			assertTrue(((String)readValue.value()).equals("String1"));
    			break;
    		case "string2":
    			assertTrue(((String)readValue.value()).equals("String2"));
    			break;
    		case "strings1":
    			ICollection<String> strings1 = (ICollection<String>) readValue.value();
    			assertTrue(strings1.size() == 3);
    			assertTrue(strings1.get(0).equals("string1-1"));
    			assertTrue(strings1.get(1).equals("string1-2"));
    			assertTrue(strings1.get(2).equals("string1-3"));
    			break;
    		case "strings2":
    			ICollection<String> strings2 = (ICollection<String>) readValue.value();
    			assertTrue(strings2.size() == 3);
    			assertTrue(strings2.get(0).equals("string2-1"));
    			assertTrue(strings2.get(1).equals("string2-2"));
    			assertTrue(strings2.get(2).equals("string2-3"));
    			break;
    		default:
    			assertTrue(false);
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
