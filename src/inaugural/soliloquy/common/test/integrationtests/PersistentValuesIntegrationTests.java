package inaugural.soliloquy.common.test.integrationtests;

import inaugural.soliloquy.common.*;
import inaugural.soliloquy.common.persistentvaluetypehandlers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.*;
import soliloquy.game.primary.specs.IGame;
import soliloquy.logger.specs.ILogger;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersistentValuesIntegrationTests {
	private IPersistentValuesHandler _persistentValuesHandler;

	// TODO: Add EntityUuid handling to this test suite
	private final String PERSISTENT_VALUES_STRING = "[{\"typeName\":\"java.lang.Boolean\",\"name\":\"bool1\",\"value\":\"true\"},{\"typeName\":\"java.lang.Boolean\",\"name\":\"bool2\",\"value\":\"false\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"integers1\",\"value\":\"java.lang.Integer\u00910\u00911\u00922\u00923\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"integers2\",\"value\":\"java.lang.Integer\u00910\u00914\u00925\u00926\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int1\",\"value\":\"123\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int2\",\"value\":\"456\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"int3\",\"value\":\"789\"},{\"typeName\":\"java.lang.String\",\"name\":\"string1\",\"value\":\"String1\"},{\"typeName\":\"java.lang.String\",\"name\":\"string2\",\"value\":\"String2\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"strings1\",\"value\":\"java.lang.String\u0091strings1Archetype\u0091string1-1\u0092string1-2\u0092string1-3\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"strings2\",\"value\":\"java.lang.String\u0091strings2Archetype\u0091string2-1\u0092string2-2\u0092string2-3\"},{\"typeName\":\"soliloquy.common.specs.IMap\\u003cjava.lang.String,java.lang.Integer\\u003e\",\"name\":\"map1\",\"value\":\"java.lang.String\\u001fjava.lang.Integer\\u001dmap1Archetype1\\u001f123123123\\u001dKey1-1\\u001f123\\u001eKey1-2\\u001f456\\u001eKey1-3\\u001f789\"},{\"typeName\":\"soliloquy.common.specs.IMap\\u003cjava.lang.String,soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\\u003e\",\"name\":\"map2\",\"value\":\"java.lang.String\\u001fsoliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\\u001dmap2Archetype1\\u001fjava.lang.Integer\u0091456456456\u0091\\u001dKey2-1\\u001fjava.lang.Integer\u0091123\u00911\u00922\u00923\\u001eKey2-2\\u001fjava.lang.Integer\u0091456\u00914\u00925\u00926\\u001eKey2-3\\u001fjava.lang.Integer\u0091789\u00917\u00928\u00929\"}]";
	
	private ICollectionFactory _collectionFactory;
	private IMapFactory _mapFactory;

	private static List<IPersistentValueToWrite> _readValues;
	
    @BeforeEach
	void setUp() {
		_collectionFactory = new CollectionFactory();

    	_persistentValuesHandler = new PersistentValuesHandler();

		IPairFactory _pairFactory = new PairFactory();
		_mapFactory = new MapFactory(_pairFactory);
    	new GenericParamsSetFactory(_persistentValuesHandler, _mapFactory);
    	new SettingFactory();
    	
    	IPersistentValueTypeHandler<Boolean> persistentBooleanHandler = new PersistentBooleanHandler();
    	IPersistentValueTypeHandler<Integer> persistentIntegerHandler = new PersistentIntegerHandler();
    	IPersistentValueTypeHandler<String> persistentStringHandler = new PersistentStringHandler();
		IPersistentValueTypeHandler<ICollection> persistentCollectionHandler = new PersistentCollectionHandler(_persistentValuesHandler, _collectionFactory);
		IPersistentValueTypeHandler<IMap> persistentMapHandler = new PersistentMapHandler(_persistentValuesHandler, _mapFactory);
    	
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentBooleanHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentIntegerHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentStringHandler);
    	_persistentValuesHandler.registerPersistentCollectionHandler(persistentCollectionHandler);
    	_persistentValuesHandler.registerPersistentMapHandler(persistentMapHandler);
    	
    	_readValues = new ArrayList<>();
    }
    
    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
	void testWriteValues() {
    	IPersistentValueToWrite persistentValueToWriteArchetype = new PersistentValueToWrite(null, null);
    	ICollection<IPersistentValueToWrite> persistentValuesToWrite =
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
    	ICollection<String> strings1 = _collectionFactory.make(new String[] {"string1-1", "string1-2", "string1-3"}, "strings1Archetype");
    	IPersistentValueToWrite strings1ToWrite = new PersistentValueToWrite("strings1", strings1);
    	persistentValuesToWrite.add(strings1ToWrite);

    	ICollection<String> strings2 = _collectionFactory.make(new String[] {"string2-1", "string2-2", "string2-3"}, "strings2Archetype");
    	IPersistentValueToWrite strings2ToWrite = new PersistentValueToWrite("strings2", strings2);
    	persistentValuesToWrite.add(strings2ToWrite);

    	// Adding a map of Strings and Integers
		IMap<String,Integer> map1 = _mapFactory.make("map1Archetype1", 123123123);
		map1.put("Key1-1", 123);
		map1.put("Key1-2", 456);
		map1.put("Key1-3", 789);
		IPersistentValueToWrite map1ToWrite = new PersistentValueToWrite("map1", map1);
		persistentValuesToWrite.add(map1ToWrite);

		// Adding a map of Strings and collections of Integers
		IMap<String,ICollection<Integer>> map2 =
				_mapFactory.make("map2Archetype1", _collectionFactory.make(456456456));
		map2.put("Key2-1", _collectionFactory.make(new Integer[] {1,2,3}, 123));
		map2.put("Key2-2", _collectionFactory.make(new Integer[] {4,5,6}, 456));
		map2.put("Key2-3", _collectionFactory.make(new Integer[] {7,8,9}, 789));
		IPersistentValueToWrite map2ToWrite = new PersistentValueToWrite("map2", map2);
		persistentValuesToWrite.add(map2ToWrite);
    	
    	String writtenValue = _persistentValuesHandler.writeValues(persistentValuesToWrite);
    	
    	assertEquals(PERSISTENT_VALUES_STRING, writtenValue);
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
	void testReadValues() throws Exception {
    	_persistentValuesHandler.readValues(PERSISTENT_VALUES_STRING, new ReadValuesAction());

		assertEquals(13, _readValues.size());
    	
    	for (IPersistentValueToWrite readValue : _readValues)
    	{
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
    			assertEquals("strings1Archetype", strings1.getArchetype());
    			break;
    		case "strings2":
    			ICollection<String> strings2 = (ICollection<String>) readValue.value();
				assertEquals(3, strings2.size());
				assertEquals("string2-1", strings2.get(0));
				assertEquals("string2-2", strings2.get(1));
				assertEquals("string2-3", strings2.get(2));
				assertEquals("strings2Archetype", strings2.getArchetype());
    			break;
			case "map1":
				IMap<String,Integer> map1 = (IMap<String,Integer>) readValue.value();
				assertEquals(3, map1.size());
				assertEquals(123, (int) map1.get("Key1-1"));
				assertEquals(456, (int) map1.get("Key1-2"));
				assertEquals(789, (int) map1.get("Key1-3"));
				break;
			case "map2":
				IMap<String,ICollection<Integer>> map2 =
						(IMap<String,ICollection<Integer>>) readValue.value();
				assertEquals(3, map2.size());
				assertEquals("map2Archetype1", map2.getFirstArchetype());
				assertEquals(456456456, (int) map2.getSecondArchetype().getArchetype());

				ICollection<Integer> map2integers1 = map2.get("Key2-1");
				assertEquals(3, map2integers1.size());
				assertTrue(map2integers1.contains(1));
				assertTrue(map2integers1.contains(2));
				assertTrue(map2integers1.contains(3));
				assertEquals(123, (int) map2integers1.getArchetype());

				ICollection<Integer> map2integers2 = map2.get("Key2-2");
				assertEquals(3, map2integers2.size());
				assertTrue(map2integers2.contains(4));
				assertTrue(map2integers2.contains(5));
				assertTrue(map2integers2.contains(6));
				assertEquals(456, (int) map2integers2.getArchetype());

				ICollection<Integer> map2integers3 = map2.get("Key2-3");
				assertEquals(3, map2integers3.size());
				assertTrue(map2integers3.contains(7));
				assertTrue(map2integers3.contains(8));
				assertTrue(map2integers3.contains(9));
				assertEquals(789, (int) map2integers3.getArchetype());
				break;
    		default:
				throw new Exception();
    		}
    	}
    }
    
    private class PersistentValueToWrite<T> extends CanGetInterfaceName
			implements IPersistentValueToWrite<T> {
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
			return getProperTypeName(VALUE);
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
    
    private class ReadValuesAction implements IAction<IPersistentValueToWrite> {
		@Override
		public String id() throws IllegalStateException {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public IPersistentValueToWrite getArchetype() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public String getInterfaceName() {
			// Stub method
			throw new UnsupportedOperationException();
		}

		@Override
		public void run(IPersistentValueToWrite input) throws IllegalArgumentException {
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
