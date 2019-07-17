package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentGenericParamsSetHandler;
import inaugural.soliloquy.common.test.stubs.GenericParamsSetFactoryStub;
import inaugural.soliloquy.common.test.stubs.GenericParamsSetStub;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.GenericParamsSetFactory;
import soliloquy.specs.common.infrastructure.GenericParamsSet;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.infrastructure.PersistentValueTypeHandler;
import soliloquy.specs.common.infrastructure.PersistentValuesHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentGenericParamsSetHandlerTests {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new PersistentValuesHandlerStub();
    private final GenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY =
            new GenericParamsSetFactoryStub();
    private final GenericParamsSet GENERIC_PARAMS_SET = new GenericParamsSetStub();

    private final String STRING_PARAM_1_NAME = "stringParam1";
    private final String STRING_PARAM_1_VALUE = "value1";
    private final String STRING_PARAM_2_NAME = "stringParam2";
    private final String STRING_PARAM_2_VALUE = "value2";
    private final String STRING_PARAM_3_NAME = "stringParam3";
    private final String STRING_PARAM_3_VALUE = "value3";
    private final String INT_PARAM_1_NAME = "integerParam1";
    private final Integer INT_PARAM_1_VALUE = 123;
    private final String INT_PARAM_2_NAME = "integerParam2";
    private final Integer INT_PARAM_2_VALUE = 456;
    private final String INT_PARAM_3_NAME = "integerParam3";
    private final Integer INT_PARAM_3_VALUE = 789;

    private final String VALUES_STRING =
            "[{\"typeName\":\"java.lang.String\"," +
                    "\"paramNames\":[\"stringParam3\",\"stringParam2\",\"stringParam1\"]," +
                    "\"paramValues\":[\"value3\",\"value2\",\"value1\"]}," +
                    "{\"typeName\":\"java.lang.Integer\"," +
                    "\"paramNames\":[\"integerParam2\",\"integerParam1\",\"integerParam3\"]," +
                    "\"paramValues\":[\"456\",\"123\",\"789\"]}]";

    private PersistentValueTypeHandler<GenericParamsSet> _persistentGenericParamsSetHandler;

    @BeforeEach
    void setUp() {
        _persistentGenericParamsSetHandler =
                new PersistentGenericParamsSetHandler(PERSISTENT_VALUES_HANDLER,
                        GENERIC_PARAMS_SET_FACTORY);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                GenericParamsSet.class.getCanonicalName() + ">",
                _persistentGenericParamsSetHandler.getInterfaceName());
    }

    @Test
    void testWrite() {
        GENERIC_PARAMS_SET.addParam(STRING_PARAM_1_NAME, STRING_PARAM_1_VALUE);
        GENERIC_PARAMS_SET.addParam(STRING_PARAM_2_NAME, STRING_PARAM_2_VALUE);
        GENERIC_PARAMS_SET.addParam(STRING_PARAM_3_NAME, STRING_PARAM_3_VALUE);
        GENERIC_PARAMS_SET.addParam(INT_PARAM_1_NAME, INT_PARAM_1_VALUE);
        GENERIC_PARAMS_SET.addParam(INT_PARAM_2_NAME, INT_PARAM_2_VALUE);
        GENERIC_PARAMS_SET.addParam(INT_PARAM_3_NAME, INT_PARAM_3_VALUE);
        assertEquals(VALUES_STRING, _persistentGenericParamsSetHandler.write(GENERIC_PARAMS_SET));
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentGenericParamsSetHandler.write(null));
    }

    @Test
    void testRead() {
        GenericParamsSet genericParamsSet =
                _persistentGenericParamsSetHandler.read(VALUES_STRING);

        assertNotNull(genericParamsSet);
        Map<String,String> stringParams =
                genericParamsSet.getParamsSet(String.class.getCanonicalName());
        assertNotNull(stringParams);
        assertEquals(3, stringParams.size());
        assertEquals(STRING_PARAM_1_VALUE, stringParams.get(STRING_PARAM_1_NAME));
        assertEquals(STRING_PARAM_2_VALUE, stringParams.get(STRING_PARAM_2_NAME));
        assertEquals(STRING_PARAM_3_VALUE, stringParams.get(STRING_PARAM_3_NAME));
        Map<String,Integer> intParams =
                genericParamsSet.getParamsSet(Integer.class.getCanonicalName());
        assertNotNull(intParams);
        assertEquals(3, intParams.size());
        assertEquals(INT_PARAM_1_VALUE, intParams.get(INT_PARAM_1_NAME));
        assertEquals(INT_PARAM_2_VALUE, intParams.get(INT_PARAM_2_NAME));
        assertEquals(INT_PARAM_3_VALUE, intParams.get(INT_PARAM_3_NAME));
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentGenericParamsSetHandler.read(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentGenericParamsSetHandler.read(""));
    }
}
