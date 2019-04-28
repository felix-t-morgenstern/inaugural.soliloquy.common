package inaugural.soliloquy.common.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.PersistentVariableFactory;
import soliloquy.common.specs.IPersistentVariable;
import soliloquy.common.specs.IPersistentVariableFactory;

import static org.junit.jupiter.api.Assertions.*;

class PersistentVariableFactoryTests {
	private IPersistentVariableFactory _persistentVariableFactory;
	
    @BeforeEach
	void setUp() {
		_persistentVariableFactory = new PersistentVariableFactory();
    }
	
    @Test
	void testMake() {
		Double persistentVariableValue = 1.23;
		String persistentVariableName = "persistentVariableName";
		IPersistentVariable persistentVariable =
				_persistentVariableFactory.make(persistentVariableName, persistentVariableValue);

		assertNotNull(persistentVariable);
		assertEquals(persistentVariableName, persistentVariable.getName());
		assertSame(persistentVariableValue, persistentVariable.getValue());
	}

	@Test
	void testGetInterfaceName() {
    	assertEquals(IPersistentVariableFactory.class.getCanonicalName(),
				_persistentVariableFactory.getInterfaceName());
	}
}
