package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.PersistentVariableFactory;
import soliloquy.common.specs.IPersistentVariable;

public class PersistentVariableFactoryTests {
	private final String PERSISTENT_VARIABLE_NAME = "persistentVariableName";
	private final Double PERSISTENT_VARIABLE_VALUE = 1.23;
	
	private PersistentVariableFactory _persistentVariableFactory;
	
    @BeforeEach
    protected void setUp() throws Exception
    {
		_persistentVariableFactory = new PersistentVariableFactory();
    }
	
    @Test
	public void testMake()
	{
		IPersistentVariable persistentVariable = _persistentVariableFactory.make(PERSISTENT_VARIABLE_NAME, PERSISTENT_VARIABLE_VALUE);
		
		assertTrue(persistentVariable != null);
		assertTrue(persistentVariable instanceof IPersistentVariable);
		assertTrue(persistentVariable.getName().equals(PERSISTENT_VARIABLE_NAME));
		assertTrue(persistentVariable.getValue() == PERSISTENT_VARIABLE_VALUE);
	}
}
