package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.common.GenericParamsSet;
import inaugural.soliloquy.common.GenericParamsSetFactory;
import inaugural.soliloquy.common.test.stubs.MapFactoryStub;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPersistentValuesHandler;

public class GenericParamsSetFactoryTests {
	private GenericParamsSetFactory _genericParamsSetFactory;
	
	private IPersistentValuesHandler _persistentValuesHandlerMock;
	
	private final IMapFactory MAP_FACTORY = new MapFactoryStub();
	
	private static final String PERSISTENT_VARIABLE_WRITE_VALUE = "GenericString";

    @BeforeEach
    protected void setUp() throws Exception {
    	_persistentValuesHandlerMock = mock(IPersistentValuesHandler.class);
    	when(_persistentValuesHandlerMock.writeValues(any())).thenReturn(PERSISTENT_VARIABLE_WRITE_VALUE);
    	when(_persistentValuesHandlerMock.makePersistentValueToWrite(any(),any())).thenReturn(null);

    	_genericParamsSetFactory = new GenericParamsSetFactory(_persistentValuesHandlerMock, MAP_FACTORY);
    	
    }

    @Test
    public void testMake() {
    	IGenericParamsSet genericParamsSet = _genericParamsSetFactory.make();
    	assertTrue(genericParamsSet instanceof GenericParamsSet);
    	
    	genericParamsSet.addParam("Blah", 123);
    	assertTrue((Integer) genericParamsSet.getParam(Integer.class.getCanonicalName(), "Blah") == 123);
    	assertTrue(genericParamsSet.write().equals(PERSISTENT_VARIABLE_WRITE_VALUE));
    }
}
