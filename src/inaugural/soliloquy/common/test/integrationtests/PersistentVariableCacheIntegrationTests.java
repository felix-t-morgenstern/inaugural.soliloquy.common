package inaugural.soliloquy.common.test.integrationtests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.CollectionFactory;
import inaugural.soliloquy.common.MapFactory;
import inaugural.soliloquy.common.PairFactory;
import inaugural.soliloquy.common.PersistentValuesHandler;
import inaugural.soliloquy.common.PersistentVariableCache;
import inaugural.soliloquy.common.PersistentVariableFactory;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentBooleanHandler;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentIntegerHandler;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentIntegersHandler;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentStringHandler;
import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentStringsHandler;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.ICollectionFactory;
import soliloquy.common.specs.IPairFactory;
import soliloquy.common.specs.IPersistentValueTypeHandler;
import soliloquy.common.specs.IPersistentValuesHandler;
import soliloquy.common.specs.IPersistentVariable;
import soliloquy.common.specs.IPersistentVariableFactory;

public class PersistentVariableCacheIntegrationTests {
	private ICollectionFactory _collectionFactory;
	private IPairFactory _pairFactory;
	private IPersistentVariableFactory _persistentVariableFactory;
	
	private IPersistentValuesHandler _persistentValuesHandler;
	
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
	private final String VARIABLE_5_VALUE_1 = "Variable5Value1";
	private final String VARIABLE_5_VALUE_2 = "Variable5Value2";
	private final String VARIABLE_5_VALUE_3 = "Variable5Value3";
    
    @BeforeEach
    protected void setUp() throws Exception
    {
    	_collectionFactory = new CollectionFactory();
    	_pairFactory = new PairFactory();
    	_persistentVariableFactory = new PersistentVariableFactory();

    	new MapFactory(_pairFactory);
    	
    	_persistentValuesHandler = new PersistentValuesHandler();
    	
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
    	
    	IPersistentVariable archetype = _persistentVariableFactory.make("name", "value");
    	
    	_persistentVariableCache = new PersistentVariableCache(_pairFactory, "", archetype,
    			_collectionFactory, _persistentVariableFactory, _persistentValuesHandler);
    	
    	_variable3Value = _collectionFactory.make(0);
    	_variable3Value.add(VARIABLE_3_VALUE_1);
    	_variable3Value.add(VARIABLE_3_VALUE_2);
    	_variable3Value.add(VARIABLE_3_VALUE_3);
    	
    	_variable5Value = _collectionFactory.make("");
    	_variable5Value.add("Variable5Value1");
    	_variable5Value.add("Variable5Value2");
    	_variable5Value.add("Variable5Value3");
    }

    @Test
    public void testWrite()
    {
    	String expectedValue = "[{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"Variable3\",\"value\":\"123,456,789\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"Variable2\",\"value\":\"123\"},{\"typeName\":\"java.lang.Boolean\",\"name\":\"Variable1\",\"value\":\"true\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"Variable5\",\"value\":\"Variable5Value1\\u001fVariable5Value2\\u001fVariable5Value3\"},{\"typeName\":\"java.lang.String\",\"name\":\"Variable4\",\"value\":\"Variable4Value\"}]";
    	
    	IPersistentVariable variable1 = _persistentVariableFactory.make(VARIABLE_1_NAME, VARIABLE_1_VALUE);
    	IPersistentVariable variable2 = _persistentVariableFactory.make(VARIABLE_2_NAME, VARIABLE_2_VALUE);
    	IPersistentVariable variable3 = _persistentVariableFactory.make(VARIABLE_3_NAME, _variable3Value);
    	IPersistentVariable variable4 = _persistentVariableFactory.make(VARIABLE_4_NAME, VARIABLE_4_VALUE);
    	IPersistentVariable variable5 = _persistentVariableFactory.make(VARIABLE_5_NAME, _variable5Value);

    	_persistentVariableCache.put(variable1);
    	_persistentVariableCache.put(variable2);
    	_persistentVariableCache.put(variable3);
    	_persistentVariableCache.put(variable4);
    	_persistentVariableCache.put(variable5);
    	
    	String writtenValue = _persistentVariableCache.write();
    	
    	assertTrue(expectedValue.equals(writtenValue));
    }

    @Test
    public void testRead()
    {
    	String valueToRead = "[{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.Integer\\u003e\",\"name\":\"Variable3\",\"value\":\"123,456,789\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"Variable2\",\"value\":\"123\"},{\"typeName\":\"java.lang.Boolean\",\"name\":\"Variable1\",\"value\":\"true\"},{\"typeName\":\"soliloquy.common.specs.ICollection\\u003cjava.lang.String\\u003e\",\"name\":\"Variable5\",\"value\":\"Variable5Value1\\u001fVariable5Value2\\u001fVariable5Value3\"},{\"typeName\":\"java.lang.String\",\"name\":\"Variable4\",\"value\":\"Variable4Value\"}]";
    	
    	_persistentVariableCache.read(valueToRead, false);
    	
    	assertTrue(_persistentVariableCache.size() == 5);
    	assertTrue(_persistentVariableCache.get(VARIABLE_1_NAME).getValue().equals(VARIABLE_1_VALUE));
    	assertTrue(_persistentVariableCache.get(VARIABLE_2_NAME).getValue().equals(VARIABLE_2_VALUE));
    	ICollection<Integer> variable3Value = _persistentVariableCache.get(VARIABLE_3_NAME).getValue();
    	assertTrue(variable3Value.size() == 3);
    	assertTrue(variable3Value.contains(VARIABLE_3_VALUE_1));
    	assertTrue(variable3Value.contains(VARIABLE_3_VALUE_2));
    	assertTrue(variable3Value.contains(VARIABLE_3_VALUE_3));
    	assertTrue(_persistentVariableCache.get(VARIABLE_4_NAME).getValue().equals(VARIABLE_4_VALUE));
    	ICollection<String> variable5Value = _persistentVariableCache.get(VARIABLE_5_NAME).getValue();
    	assertTrue(variable5Value.size() == 3);
    	assertTrue(variable5Value.contains(VARIABLE_5_VALUE_1));
    	assertTrue(variable5Value.contains(VARIABLE_5_VALUE_2));
    	assertTrue(variable5Value.contains(VARIABLE_5_VALUE_3));
    }
}
