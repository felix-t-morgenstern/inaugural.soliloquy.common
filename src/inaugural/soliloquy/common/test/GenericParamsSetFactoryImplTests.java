package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.common.GenericParamsSetImpl;
import inaugural.soliloquy.common.GenericParamsSetFactoryImpl;
import inaugural.soliloquy.common.test.stubs.MapFactoryStub;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;

class GenericParamsSetFactoryImplTests {
    private GenericParamsSetFactoryImpl _genericParamsSetFactory;

    private final MapFactory MAP_FACTORY = new MapFactoryStub();

    @BeforeEach
    void setUp() {
        _genericParamsSetFactory = new GenericParamsSetFactoryImpl(new PersistentValuesHandlerStub(),
                MAP_FACTORY);

    }

    @Test
    void testMake() {
        GenericParamsSet genericParamsSet = _genericParamsSetFactory.make();
        assertTrue(genericParamsSet instanceof GenericParamsSetImpl);
    }
}
