package inaugural.soliloquy.common.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.PersistentVariable;
import soliloquy.common.specs.IPersistentValueToWrite;
import soliloquy.common.specs.IPersistentVariable;
import soliloquy.common.specs.IPersistentVariableCache;

import static org.junit.jupiter.api.Assertions.*;

class PersistentVariableTests {
	private IPersistentVariable _persistentVariable;
	
	private final String PERSISTENT_VARIABLE_NAME = "PersistentVariableName";
	private final Integer PERSISTENT_VARIABLE_DEFAULT_VALUE = 123;

	@BeforeEach
	void setUp() {
		_persistentVariable = new PersistentVariable(PERSISTENT_VARIABLE_NAME, PERSISTENT_VARIABLE_DEFAULT_VALUE);
    }
	
    @Test
	void testGetName() {
		assertEquals(_persistentVariable.getName(), PERSISTENT_VARIABLE_NAME);
	}

    @Test
	void testGetValue() {
		assertSame(_persistentVariable.getValue(), PERSISTENT_VARIABLE_DEFAULT_VALUE);
	}

    @Test
	void testSetValue() {
		Integer persistentVariableAltValue = 456;
		_persistentVariable.setValue(persistentVariableAltValue);

		assertSame(_persistentVariable.getValue(), persistentVariableAltValue);
	}

    @Test
	void testToWriteRepresentation() {
		IPersistentValueToWrite<Integer> persistentValueToWrite = _persistentVariable.toWriteRepresentation();

		assertEquals(PERSISTENT_VARIABLE_NAME, persistentValueToWrite.name());
		assertSame(PERSISTENT_VARIABLE_DEFAULT_VALUE, persistentValueToWrite.value());
		assertEquals(PERSISTENT_VARIABLE_DEFAULT_VALUE.getClass().getCanonicalName(),
				persistentValueToWrite.typeName());
		assertEquals(IPersistentValueToWrite.class.getCanonicalName() + "<"
				+ PERSISTENT_VARIABLE_DEFAULT_VALUE.getClass().getCanonicalName() + ">",
				persistentValueToWrite.getInterfaceName());
	}

	@Test
	void testGetInterfaceName() {
		assertEquals(IPersistentVariable.class.getCanonicalName(),
				_persistentVariable.getInterfaceName());
	}
}
