package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
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
    	_genericParamsSetFactory = new GenericParamsSetFactory(new PersistentValuesHandlerStub(), MAP_FACTORY);
    	
    }

    @Test
	void testMake() {
    	IGenericParamsSet genericParamsSet = _genericParamsSetFactory.make();
    	assertTrue(genericParamsSet instanceof GenericParamsSet);
    }
}
