package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.common.GenericParamsSet;
import inaugural.soliloquy.common.test.stubs.GenericParamsSetPersistentValuesHandlerStub;
import inaugural.soliloquy.common.test.stubs.MapFactoryStub;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IMapFactory;

class GenericParamsSetTests {
	private GenericParamsSet _genericParamsSet;
	private IMap<String,Integer> _mapIntMock;
	private IMap<String,Integer> _mapIntMock2;
	private IMap<String,Boolean> _mapBoolMock;

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
    	_mapIntMock = (IMap<String,Integer>) mock(IMap.class);
    	when(_mapIntMock.get("String")).thenReturn(123);
    	_mapIntMock2 = (IMap<String,Integer>) mock(IMap.class);
    	when(_mapIntMock2.get("String")).thenReturn(456);
    	_mapBoolMock = (IMap<String,Boolean>) mock(IMap.class);
    	when(_mapBoolMock.get("String")).thenReturn(true);
    	
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
	void testAddParamWithNullArchetype() {
		assertThrows(IllegalArgumentException.class,
				() -> _genericParamsSet.addParam(INT_PARAM_1_NAME, INT_PARAM_1_VALUE, null));
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
    	_genericParamsSet.addParamsSet(_mapIntMock, 0);
		assertSame(_genericParamsSet.<Integer>getParamsSet(Integer.class.getCanonicalName()), _mapIntMock);
    }

    @Test
	void testAddNullParamsSet() {
		assertThrows(IllegalArgumentException.class, () -> _genericParamsSet.addParamsSet(null, 0));
    }

    @Test
	void testAddParamsSetWithNullArchetype() {
		assertThrows(IllegalArgumentException.class,
				() -> _genericParamsSet.addParamsSet(_mapIntMock, null));
    }

    @Test
	void testGetNullParamsSet() {
    	IMap<String,Integer> paramsSet = _genericParamsSet.getParamsSet(Integer.class.getCanonicalName());
		assertNull(paramsSet);
    }

    @Test
	void testAddThenGetParamsSet() {
		assertNull(_genericParamsSet.getParamsSet(Integer.class.getCanonicalName()));
    	_genericParamsSet.addParamsSet(_mapIntMock, 0);
    	IMap<String,Integer> paramsSet = _genericParamsSet.getParamsSet(Integer.class.getCanonicalName());
		assertNotNull(paramsSet);
		assertEquals(123, (int) paramsSet.get("String"));
    }

    @Test
	void testAddParamsSetTwice() {
    	assertThrows(UnsupportedOperationException.class, () -> {
			_genericParamsSet.addParamsSet(_mapIntMock, 0);
			_genericParamsSet.addParamsSet(_mapIntMock2, 0);
		});
    }

    @Test
	void testParamTypes() {
    	ICollection<String> paramTypes = _genericParamsSet.paramTypes();
    	assertTrue(paramTypes.isEmpty());
    	
    	_genericParamsSet.addParamsSet(_mapIntMock, 0);
    	_genericParamsSet.addParamsSet(_mapBoolMock, false);
    	
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

    @Test
	void testRead() {
		assertNotSame("This is just some data", _genericParamsSet.getParam(String.class.getCanonicalName(), "DummyValue"));
    	_genericParamsSet.read("This is just some data");
		assertSame("This is just some data", _genericParamsSet.getParam(String.class.getCanonicalName(), "DummyValue"));
    	_genericParamsSet.read("This is just some more data");
		assertSame("This is just some more data", _genericParamsSet.getParam(String.class.getCanonicalName(), "DummyValue"));
    }

    @Test
	void testWrite() {
		assertSame("", _genericParamsSet.write());

    	_genericParamsSet.addParam(INT_PARAM_1_NAME, INT_PARAM_1_VALUE);
    	_genericParamsSet.addParam(INT_PARAM_2_NAME, INT_PARAM_2_VALUE);
    	_genericParamsSet.addParam(STR_PARAM_1_NAME, STR_PARAM_1_VALUE);
    	_genericParamsSet.addParam(STR_PARAM_2_NAME, STR_PARAM_2_VALUE);

    	// No idea why strings are reversed and ints are not.
		assertEquals(_genericParamsSet.write(), "Name:" + STR_PARAM_2_NAME + ",Value:" + STR_PARAM_2_VALUE + ";" +
				"Name:" + STR_PARAM_1_NAME + ",Value:" + STR_PARAM_1_VALUE + ";" +
				"Name:" + INT_PARAM_1_NAME + ",Value:" + INT_PARAM_1_VALUE + ";" +
				"Name:" + INT_PARAM_2_NAME + ",Value:" + INT_PARAM_2_VALUE + ";");
    }
}
