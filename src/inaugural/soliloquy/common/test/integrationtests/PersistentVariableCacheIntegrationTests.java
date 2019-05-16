package inaugural.soliloquy.common.test.integrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import inaugural.soliloquy.common.persistentvaluetypehandlers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.CollectionFactory;
import inaugural.soliloquy.common.MapFactory;
import inaugural.soliloquy.common.PairFactory;
import inaugural.soliloquy.common.PersistentValuesHandler;
import inaugural.soliloquy.common.PersistentVariableCache;
import inaugural.soliloquy.common.PersistentVariableFactory;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IPairFactory;
import soliloquy.common.specs.IPersistentValueTypeHandler;
import soliloquy.common.specs.IPersistentValuesHandler;
import soliloquy.common.specs.IPersistentVariable;
import soliloquy.common.specs.IPersistentVariableFactory;

class PersistentVariableCacheIntegrationTests {
	private final String PERSISTENT_VARIABLE_CACHE_STRING = "[{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"Variable3\",\"value\":\"java.lang.Integer\u00910\u0091123\u0092456\u0092789\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"Variable2\",\"value\":\"123\"},{\"typeName\":\"java.lang.Boolean\",\"name\":\"Variable1\",\"value\":\"true\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"Variable5\",\"value\":\"java.lang.String\u0091\u0091Variable5Value1\u0092Variable5Value2\u0092Variable5Value3\"},{\"typeName\":\"java.lang.String\",\"name\":\"Variable4\",\"value\":\"Variable4Value\"}]";

	private IPersistentVariableFactory _persistentVariableFactory;

	private PersistentVariableCache _persistentVariableCache;

	private final String VARIABLE_1_NAME = "Variable1";
	private final String VARIABLE_2_NAME = "Variable2";
	private final String VARIABLE_3_NAME = "Variable3";
	private final String VARIABLE_4_NAME = "Variable4";
	private final String VARIABLE_5_NAME = "Variable5";
	
	private final Boolean VARIABLE_1_VALUE = true;
	private final Integer VARIABLE_2_VALUE = 123;
	private ICollection<Integer> _variable3Value;
	private final Integer VARIABLE_3_VALUE_1 = 123;
	private final Integer VARIABLE_3_VALUE_2 = 456;
	private final Integer VARIABLE_3_VALUE_3 = 789;
	private final String VARIABLE_4_VALUE = "Variable4Value";
	private ICollection<String> _variable5Value;

	// TODO: Add EntityUuid handling to this test suite
	// TODO: Add Pair handling to this test

	@BeforeEach
	void setUp() {
		ICollectionFactory _collectionFactory = new CollectionFactory();
		IPairFactory _pairFactory = new PairFactory();
    	_persistentVariableFactory = new PersistentVariableFactory();

    	new MapFactory(_pairFactory);

		IPersistentValuesHandler _persistentValuesHandler = new PersistentValuesHandler();
    	
    	IPersistentValueTypeHandler<Boolean> persistentBooleanHandler = new PersistentBooleanHandler();
    	IPersistentValueTypeHandler<Integer> persistentIntegerHandler = new PersistentIntegerHandler();
    	IPersistentValueTypeHandler<String> persistentStringHandler = new PersistentStringHandler();
		IPersistentValueTypeHandler<ICollection> persistentCollectionHandler = new PersistentCollectionHandler(_persistentValuesHandler, _collectionFactory);
    	
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentBooleanHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentIntegerHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(persistentStringHandler);
		_persistentValuesHandler.registerPersistentCollectionHandler(persistentCollectionHandler);
    	
    	IPersistentVariable archetype = _persistentVariableFactory.make("name", "value");
    	
    	_persistentVariableCache = new PersistentVariableCache(_collectionFactory);
    	
    	_variable3Value = _collectionFactory.make(0);
    	_variable3Value.add(VARIABLE_3_VALUE_1);
    	_variable3Value.add(VARIABLE_3_VALUE_2);
    	_variable3Value.add(VARIABLE_3_VALUE_3);
    	
    	_variable5Value = _collectionFactory.make("");
    	_variable5Value.add("Variable5Value1");
    	_variable5Value.add("Variable5Value2");
    	_variable5Value.add("Variable5Value3");
    }

	// TODO: Rework this test to use the proper type handler
    @Test
	void testWrite() {
//    	IPersistentVariable variable1 = _persistentVariableFactory.make(VARIABLE_1_NAME, VARIABLE_1_VALUE);
//    	IPersistentVariable variable2 = _persistentVariableFactory.make(VARIABLE_2_NAME, VARIABLE_2_VALUE);
//    	IPersistentVariable variable3 = _persistentVariableFactory.make(VARIABLE_3_NAME, _variable3Value);
//    	IPersistentVariable variable4 = _persistentVariableFactory.make(VARIABLE_4_NAME, VARIABLE_4_VALUE);
//    	IPersistentVariable variable5 = _persistentVariableFactory.make(VARIABLE_5_NAME, _variable5Value);
//
//    	_persistentVariableCache.put(variable1);
//    	_persistentVariableCache.put(variable2);
//    	_persistentVariableCache.put(variable3);
//    	_persistentVariableCache.put(variable4);
//    	_persistentVariableCache.put(variable5);
//
//    	String writtenValue = _persistentVariableCache.write();
//
//		assertEquals(PERSISTENT_VARIABLE_CACHE_STRING, writtenValue);
    }

	// TODO: Rework this test to use the proper type handler
    @Test
	void testRead() {
//    	_persistentVariableCache.read(PERSISTENT_VARIABLE_CACHE_STRING, false);
//
//		assertEquals(5, _persistentVariableCache.size());
//		assertEquals(_persistentVariableCache.get(VARIABLE_1_NAME).getValue(), VARIABLE_1_VALUE);
//		assertEquals(_persistentVariableCache.get(VARIABLE_2_NAME).getValue(), VARIABLE_2_VALUE);
//    	ICollection<Integer> variable3Value = _persistentVariableCache.get(VARIABLE_3_NAME).getValue();
//		assertEquals(3, variable3Value.size());
//    	assertTrue(variable3Value.contains(VARIABLE_3_VALUE_1));
//    	assertTrue(variable3Value.contains(VARIABLE_3_VALUE_2));
//    	assertTrue(variable3Value.contains(VARIABLE_3_VALUE_3));
//		assertEquals(_persistentVariableCache.get(VARIABLE_4_NAME).getValue(), VARIABLE_4_VALUE);
//    	ICollection<String> variable5Value = _persistentVariableCache.get(VARIABLE_5_NAME).getValue();
//		assertEquals(3, variable5Value.size());
//		String VARIABLE_5_VALUE_1 = "Variable5Value1";
//		assertTrue(variable5Value.contains(VARIABLE_5_VALUE_1));
//		String VARIABLE_5_VALUE_2 = "Variable5Value2";
//		assertTrue(variable5Value.contains(VARIABLE_5_VALUE_2));
//		String VARIABLE_5_VALUE_3 = "Variable5Value3";
//		assertTrue(variable5Value.contains(VARIABLE_5_VALUE_3));
    }
}
