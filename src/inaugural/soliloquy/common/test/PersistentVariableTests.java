package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.PersistentVariable;
import soliloquy.common.specs.IPersistentValueToWrite;

public class PersistentVariableTests {
	private PersistentVariable _persistentVariable;
	
	private final String PERSISTENT_VARIABLE_NAME = "PersistentVariableName";
	private final Integer PERSISTENT_VARIABLE_DEFAULT_VALUE = 123;
	private final Integer PERSISTENT_VARIABLE_ALT_VALUE = 456;
	
    @BeforeEach
    protected void setUp() throws Exception {
		_persistentVariable = new PersistentVariable(PERSISTENT_VARIABLE_NAME, PERSISTENT_VARIABLE_DEFAULT_VALUE);
    }
	
    @Test
	public void testGetName() {
		assertTrue(_persistentVariable.getName().equals(PERSISTENT_VARIABLE_NAME));
	}

    @Test
	public void testGetValue() {
		assertTrue(_persistentVariable.getValue() == PERSISTENT_VARIABLE_DEFAULT_VALUE);
	}

    @Test
	public void testSetValue() {
		_persistentVariable.setValue(PERSISTENT_VARIABLE_ALT_VALUE);

		assertTrue(_persistentVariable.getValue() == PERSISTENT_VARIABLE_ALT_VALUE);
	}

    @Test
	public void testToWriteRepresentation() {
		IPersistentValueToWrite<Integer> persistentValueToWrite = _persistentVariable.toWriteRepresentation();

		assertTrue(persistentValueToWrite.name().equals(PERSISTENT_VARIABLE_NAME));
		assertTrue(persistentValueToWrite.value() == PERSISTENT_VARIABLE_DEFAULT_VALUE);
		assertTrue(persistentValueToWrite.typeName()
				.equals(PERSISTENT_VARIABLE_DEFAULT_VALUE.getClass().getCanonicalName()));
		assertTrue(persistentValueToWrite.getInterfaceName()
				.equals("soliloquy.common.specs.IPersistentValueToWrite<"
						+ PERSISTENT_VARIABLE_DEFAULT_VALUE.getClass().getCanonicalName() + ">"));
	}
}
