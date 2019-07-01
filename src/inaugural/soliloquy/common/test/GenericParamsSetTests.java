package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.*;

import inaugural.soliloquy.common.test.stubs.MapStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.common.GenericParamsSet;
import inaugural.soliloquy.common.test.stubs.GenericParamsSetPersistentValuesHandlerStub;
import inaugural.soliloquy.common.test.stubs.MapFactoryStub;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.common.infrastructure.ICollection;
import soliloquy.specs.common.infrastructure.IGenericParamsSet;
import soliloquy.specs.common.infrastructure.IMap;

class GenericParamsSetTests {
	private GenericParamsSet _genericParamsSet;
	private IMap<String,Integer> _mapOfInts;
	private IMap<String,Integer> _mapOfInts2;
	private IMap<String,Boolean> _mapOfBooleans;

	private final String INT_PARAM_1_NAME = "Int_param_1";
	private final Integer INT_PARAM_1_VALUE = 123;
	private final String INT_PARAM_2_NAME = "Int_param_2";
	private final Integer INT_PARAM_2_VALUE = 456;

	private final String STR_PARAM_1_NAME = "Str_param_1";
	private final String STR_PARAM_1_VALUE = "Str_param_1_val";
	private final String STR_PARAM_2_NAME = "Str_param_2";
	private final String STR_PARAM_2_VALUE = "Str_param_2_val";
	
	private final IMapFactory MAP_FACTORY = new MapFactoryStub();
	
    @SuppressWarnings("unchecked")
	@BeforeEach
	void setUp() {
    	_mapOfInts = new MapStub<>("", 0);
    	_mapOfInts.put("String", 123);

		_mapOfInts2 = new MapStub<>("", 0);
		_mapOfInts2.put("String", 456);

		_mapOfBooleans = new MapStub<>("", false);
		_mapOfBooleans.put("String", true);
    	
    	_genericParamsSet = new GenericParamsSet(new GenericParamsSetPersistentValuesHandlerStub(),
    			MAP_FACTORY);
    }

    @Test
	void testAddAndGetParamAndParamExists() {
		assertNull(_genericParamsSet.getParam(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	assertTrue(!_genericParamsSet.paramExists(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	
    	_genericParamsSet.addParam(INT_PARAM_1_NAME, INT_PARAM_1_VALUE);

    	assertTrue(_genericParamsSet.paramExists(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
		assertSame(_genericParamsSet.getParam(Integer.class.getCanonicalName(), INT_PARAM_1_NAME), INT_PARAM_1_VALUE);
    }

    @Test
	void testAddParamWithNullValue() {
    	assertThrows(IllegalArgumentException.class,
				() -> _genericParamsSet.addParam(INT_PARAM_1_NAME, null));
    }

    @Test
	void testRemoveParam() {
    	assertTrue(!_genericParamsSet.removeParam(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	_genericParamsSet.addParam(INT_PARAM_1_NAME, INT_PARAM_1_VALUE);
    	assertTrue(_genericParamsSet.paramExists(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	
    	assertTrue(_genericParamsSet.removeParam(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	assertTrue(!_genericParamsSet.paramExists(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    }

    @Test
	void testAddParamsSet() {
		assertNull(_genericParamsSet.getParamsSet(Integer.class.getCanonicalName()));
		assertNotEquals(_genericParamsSet.<Integer>getParamsSet(Integer.class.getCanonicalName()), _mapOfInts);

    	_genericParamsSet.addParamsSet(_mapOfInts);

		assertEquals(_genericParamsSet.<Integer>getParamsSet(Integer.class.getCanonicalName()), _mapOfInts);
    }

    @Test
	void testAddNullParamsSet() {
		assertThrows(IllegalArgumentException.class, () -> _genericParamsSet.addParamsSet(null));
    }

    @Test
	void testGetNullParamsSet() {
    	IMap<String,Integer> paramsSet = _genericParamsSet.getParamsSet(Integer.class.getCanonicalName());
		assertNull(paramsSet);
    }

    @Test
	void testAddThenGetParamsSet() {
		assertNull(_genericParamsSet.getParamsSet(Integer.class.getCanonicalName()));
    	_genericParamsSet.addParamsSet(_mapOfInts);
    	IMap<String,Integer> paramsSet = _genericParamsSet.getParamsSet(Integer.class.getCanonicalName());
		assertNotNull(paramsSet);
		assertEquals(123, (int) paramsSet.get("String"));
    }

    @Test
	void testAddParamsSetTwice() {
    	assertThrows(UnsupportedOperationException.class, () -> {
			_genericParamsSet.addParamsSet(_mapOfInts);
			_genericParamsSet.addParamsSet(_mapOfInts2);
		});
    }

    @Test
	void testParamTypes() {
    	ICollection<String> paramTypes = _genericParamsSet.paramTypes();
    	assertTrue(paramTypes.isEmpty());
    	
    	_genericParamsSet.addParamsSet(_mapOfInts);
    	_genericParamsSet.addParamsSet(_mapOfBooleans);
    	
    	paramTypes = _genericParamsSet.paramTypes();
    	assertTrue(!paramTypes.isEmpty());
		assertEquals(2, paramTypes.size());
    	assertTrue(paramTypes.contains(Integer.class.getCanonicalName()));
    	assertTrue(paramTypes.contains(Integer.class.getCanonicalName()));
    }

    @Test
	void testMakeClone() {
    	_genericParamsSet.addParam("Int_Param_1",1);
    	_genericParamsSet.addParam("Int_Param_2",2);
    	_genericParamsSet.addParam("Bool_Param_1",true);
    	_genericParamsSet.addParam("Bool_Param_2",false);
    	
    	IGenericParamsSet cloned = _genericParamsSet.makeClone();

		assertEquals(2, cloned.paramTypes().size());
    	
    	IMap<String,Integer> intParams = cloned.getParamsSet(Integer.class.getCanonicalName());
		assertEquals(2, intParams.size());
		assertEquals(1, (int) intParams.get("Int_Param_1"));
		assertEquals(2, (int) intParams.get("Int_Param_2"));
    	
    	IMap<String,Boolean> boolParams = cloned.getParamsSet(Boolean.class.getCanonicalName());
		assertEquals(2, boolParams.size());
		assertEquals(true, boolParams.get("Bool_Param_1"));
		assertEquals(false, boolParams.get("Bool_Param_2"));
    }
}
