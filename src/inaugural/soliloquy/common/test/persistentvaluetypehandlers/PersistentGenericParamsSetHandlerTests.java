package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentGenericParamsSetHandler;
import inaugural.soliloquy.common.test.stubs.GenericParamsSetFactoryStub;
import inaugural.soliloquy.common.test.stubs.GenericParamsSetStub;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.common.specs.*;

import static org.junit.jupiter.api.Assertions.*;

class PersistentGenericParamsSetHandlerTests {
    private final IPersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new PersistentValuesHandlerStub();
    private final IGenericParamsSetFactory GENERIC_PARAMS_SET_FACTORY =
            new GenericParamsSetFactoryStub();
    private final IGenericParamsSet GENERIC_PARAMS_SET = new GenericParamsSetStub();

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

    private final String VALUES_STRING = "java.lang.String\u0093" +
            "stringParam3\u0097value3\u0096stringParam2\u0097value2\u0096stringParam1\u0097value1" +
            "\u0095java.lang.Integer" +
            "\u0093integerParam2\u0097456\u0096integerParam1\u0097123\u0096integerParam3\u0097789";

    private IPersistentValueTypeHandler<IGenericParamsSet> _persistentGenericParamsSetHandler;

    @BeforeEach
    void setUp() {
        _persistentGenericParamsSetHandler =
                new PersistentGenericParamsSetHandler(PERSISTENT_VALUES_HANDLER,
                        GENERIC_PARAMS_SET_FACTORY);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(IPersistentValueTypeHandler.class.getCanonicalName() + "<" +
                IGenericParamsSet.class.getCanonicalName() + ">",
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
        IGenericParamsSet genericParamsSet =
                _persistentGenericParamsSetHandler.read(VALUES_STRING);

        assertNotNull(genericParamsSet);
        IMap<String,String> stringParams =
                genericParamsSet.getParamsSet(String.class.getCanonicalName());
        assertNotNull(stringParams);
        assertEquals(3, stringParams.size());
        assertEquals(STRING_PARAM_1_VALUE, stringParams.get(STRING_PARAM_1_NAME));
        assertEquals(STRING_PARAM_2_VALUE, stringParams.get(STRING_PARAM_2_NAME));
        assertEquals(STRING_PARAM_3_VALUE, stringParams.get(STRING_PARAM_3_NAME));
        IMap<String,Integer> intParams =
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
                () -> _persistentGenericParamsSetHandler.read("java.lang.String\u0093" +
                        "stringParam3\u0097value3\u0097InvalidField\u0096" +
                        "stringParam2\u0097value2\u0096" +
                        "stringParam1\u0097value1" +
                        "\u0095" +
                        "java.lang.Integer" +
                        "\u0093integerParam2\u0097456\u0096" +
                        "integerParam1\u0097123\u0096" +
                        "integerParam3\u0097789"));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentGenericParamsSetHandler.read("java.lang.String\u0093" +
                        "stringParam3\u0097value3\u0096" +
                        "stringParam2\u0097value2\u0096" +
                        "stringParam1\u0097value1" +
                        "\u0093InvalidField" +
                        "\u0095" +
                        "java.lang.Integer" +
                        "\u0093integerParam2\u0097456\u0096" +
                        "integerParam1\u0097123\u0096" +
                        "integerParam3\u0097789"));
    }
}
