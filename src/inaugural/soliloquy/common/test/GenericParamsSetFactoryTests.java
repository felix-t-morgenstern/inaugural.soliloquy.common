package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

class GenericParamsSetFactoryTests {
	private GenericParamsSetFactory _genericParamsSetFactory;

	private final IMapFactory MAP_FACTORY = new MapFactoryStub();
	
	private static final String PERSISTENT_VARIABLE_WRITE_VALUE = "GenericString";

    @BeforeEach
	void setUp() {
		IPersistentValuesHandler persistentValuesHandlerMock = mock(IPersistentValuesHandler.class);
    	when(persistentValuesHandlerMock.writeValues(any())).thenReturn(PERSISTENT_VARIABLE_WRITE_VALUE);
    	when(persistentValuesHandlerMock.makePersistentValueToWrite(any(),any())).thenReturn(null);

    	_genericParamsSetFactory = new GenericParamsSetFactory(persistentValuesHandlerMock, MAP_FACTORY);
    	
    }

    @Test
	void testMake() {
    	IGenericParamsSet genericParamsSet = _genericParamsSetFactory.make();
    	assertTrue(genericParamsSet instanceof GenericParamsSet);
    	
    	genericParamsSet.addParam("Blah", 123);
		assertEquals(123, (int) (Integer) genericParamsSet.getParam(Integer.class.getCanonicalName(), "Blah"));
		assertEquals(genericParamsSet.write(), PERSISTENT_VARIABLE_WRITE_VALUE);
    }
}
