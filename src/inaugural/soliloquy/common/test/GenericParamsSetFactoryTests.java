package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.common.GenericParamsSet;
import inaugural.soliloquy.common.GenericParamsSetFactory;
import inaugural.soliloquy.common.test.stubs.MapFactoryStub;
import soliloquy.specs.common.factories.IMapFactory;
import soliloquy.specs.common.infrastructure.IGenericParamsSet;

class GenericParamsSetFactoryTests {
	private GenericParamsSetFactory _genericParamsSetFactory;

	private final IMapFactory MAP_FACTORY = new MapFactoryStub();

	@BeforeEach
	void setUp() {
    	_genericParamsSetFactory = new GenericParamsSetFactory(new PersistentValuesHandlerStub(),
				MAP_FACTORY);
    	
    }

    @Test
	void testMake() {
    	IGenericParamsSet genericParamsSet = _genericParamsSetFactory.make();
    	assertTrue(genericParamsSet instanceof GenericParamsSet);
    }
}
