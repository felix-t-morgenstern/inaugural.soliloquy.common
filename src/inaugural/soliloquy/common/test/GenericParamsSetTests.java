package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
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

public class GenericParamsSetTests {
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
    protected void setUp() throws Exception {
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
    public void testAddAndGetParamAndParamExists() {
    	assertTrue(_genericParamsSet.getParam(Integer.class.getCanonicalName(), INT_PARAM_1_NAME) == null);
    	assertTrue(!_genericParamsSet.paramExists(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	
    	_genericParamsSet.addParam(INT_PARAM_1_NAME, INT_PARAM_1_VALUE);

    	assertTrue(_genericParamsSet.paramExists(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	assertTrue(_genericParamsSet.getParam(Integer.class.getCanonicalName(), INT_PARAM_1_NAME) == INT_PARAM_1_VALUE);
    }

    @Test
    public void testAddParamWithNullValue() {
    	try {
    		_genericParamsSet.addParam(INT_PARAM_1_NAME, null);
    		assertTrue(false);
    	} catch (IllegalArgumentException e) {
    		assertTrue(true);
    	} catch (Exception e) {
    		assertTrue(false);
    	}
    }

    @Test
    public void testAddParamWithNullArchetype() {
    	try {
    		_genericParamsSet.addParam(INT_PARAM_1_NAME, INT_PARAM_1_VALUE, null);
    		assertTrue(false);
    	} catch (IllegalArgumentException e) {
    		assertTrue(true);
    	} catch (Exception e) {
    		assertTrue(false);
    	}
    }

    @Test
    public void testRemoveParam() {
    	assertTrue(!_genericParamsSet.removeParam(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	_genericParamsSet.addParam(INT_PARAM_1_NAME, INT_PARAM_1_VALUE);
    	assertTrue(_genericParamsSet.paramExists(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	
    	assertTrue(_genericParamsSet.removeParam(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    	assertTrue(!_genericParamsSet.paramExists(Integer.class.getCanonicalName(), INT_PARAM_1_NAME));
    }

    @Test
    public void testAddParamsSet() {
    	assertTrue(_genericParamsSet.getParamsSet(Integer.class.getCanonicalName()) == null);
    	_genericParamsSet.addParamsSet(_mapIntMock, 0);
    	assertTrue((IMap<String,Integer>)_genericParamsSet.<Integer>getParamsSet(Integer.class.getCanonicalName()) == _mapIntMock);
    }

    @Test
    public void testAddNullParamsSet() {
    	try {
    		_genericParamsSet.addParamsSet(null, 0);
    		assertTrue(false);
    	} catch (IllegalArgumentException e) {
    		assertTrue(true);
    	} catch (Exception e) {
    		assertTrue(false);
    	}
    }

    @Test
    public void testAddParamsSetWithNullArchetype() {
    	try {
    		_genericParamsSet.addParamsSet(_mapIntMock, null);
    		assertTrue(false);
    	} catch (IllegalArgumentException e) {
    		assertTrue(true);
    	} catch (Exception e) {
    		assertTrue(false);
    	}
    }

    @Test
    public void testGetNullParamsSet() {
    	IMap<String,Integer> paramsSet = _genericParamsSet.getParamsSet(Integer.class.getCanonicalName());
    	assertTrue(paramsSet == null);
    }

    @Test
    public void testAddThenGetParamsSet() {
    	assertTrue(_genericParamsSet.getParamsSet(Integer.class.getCanonicalName()) == null);
    	_genericParamsSet.addParamsSet(_mapIntMock, 0);
    	IMap<String,Integer> paramsSet = _genericParamsSet.getParamsSet(Integer.class.getCanonicalName());
    	assertTrue(paramsSet != null);
    	assertTrue(paramsSet.get("String") == 123);
    }

    @Test
    public void testAddParamsSetTwice() {
    	try {
    		_genericParamsSet.addParamsSet(_mapIntMock, 0);
    		_genericParamsSet.addParamsSet(_mapIntMock2, 0);
    		assertTrue(false);
    	} catch(UnsupportedOperationException e) {
    		assertTrue(true);
    	} catch(Exception e) {
    		assertTrue(false);
    	}
    }

    @Test
    public void testParamTypes() {
    	ICollection<String> paramTypes = _genericParamsSet.paramTypes();
    	assertTrue(paramTypes.isEmpty());
    	
    	_genericParamsSet.addParamsSet(_mapIntMock, 0);
    	_genericParamsSet.addParamsSet(_mapBoolMock, false);
    	
    	paramTypes = _genericParamsSet.paramTypes();
    	assertTrue(!paramTypes.isEmpty());
    	assertTrue(paramTypes.size() == 2);
    	assertTrue(paramTypes.contains(Integer.class.getCanonicalName()));
    	assertTrue(paramTypes.contains(Integer.class.getCanonicalName()));
    }

    @Test
    public void testMakeClone() {
    	_genericParamsSet.addParam("Int_Param_1",1);
    	_genericParamsSet.addParam("Int_Param_2",2);
    	_genericParamsSet.addParam("Bool_Param_1",true);
    	_genericParamsSet.addParam("Bool_Param_2",false);
    	
    	IGenericParamsSet cloned = _genericParamsSet.makeClone();
    	
    	assertTrue(cloned.paramTypes().size() == 2);
    	
    	IMap<String,Integer> intParams = cloned.getParamsSet(Integer.class.getCanonicalName());
    	assertTrue(intParams.size() == 2);
    	assertTrue(intParams.get("Int_Param_1") == 1);
    	assertTrue(intParams.get("Int_Param_2") == 2);
    	
    	IMap<String,Boolean> boolParams = cloned.getParamsSet(Boolean.class.getCanonicalName());
    	assertTrue(boolParams.size() == 2);
    	assertTrue(boolParams.get("Bool_Param_1") == true);
    	assertTrue(boolParams.get("Bool_Param_2") == false);
    }

    @Test
    public void testRead() {
    	assertTrue(_genericParamsSet.getParam(String.class.getCanonicalName(), "DummyValue") != "This is just some data");
    	_genericParamsSet.read("This is just some data", false);
    	try {
        	_genericParamsSet.read("This is just some data", false);
    		assertTrue(false);
    	} catch(IllegalArgumentException e) {
    		assertTrue(true);
    	} catch(Exception e) {
    		assertTrue(false);
    	}
    	assertTrue(_genericParamsSet.getParam(String.class.getCanonicalName(), "DummyValue") == "This is just some data");
    	_genericParamsSet.read("This is just some more data", true);
    	assertTrue(_genericParamsSet.getParam(String.class.getCanonicalName(), "DummyValue") == "This is just some more data");
    }

    @Test
    public void testWrite() {
    	assertTrue(_genericParamsSet.write() == "");

    	_genericParamsSet.addParam(INT_PARAM_1_NAME, INT_PARAM_1_VALUE);
    	_genericParamsSet.addParam(INT_PARAM_2_NAME, INT_PARAM_2_VALUE);
    	_genericParamsSet.addParam(STR_PARAM_1_NAME, STR_PARAM_1_VALUE);
    	_genericParamsSet.addParam(STR_PARAM_2_NAME, STR_PARAM_2_VALUE);

    	// No idea why strings are reversed and ints are not.
    	assertTrue(_genericParamsSet.write().equals("Name:"+STR_PARAM_2_NAME+",Value:"+STR_PARAM_2_VALUE+";"+ 
    			"Name:"+STR_PARAM_1_NAME+",Value:"+STR_PARAM_1_VALUE+";" +
    			"Name:"+INT_PARAM_1_NAME+",Value:"+INT_PARAM_1_VALUE+";" + 
    			"Name:"+INT_PARAM_2_NAME+",Value:"+INT_PARAM_2_VALUE+";"));
    }
}
