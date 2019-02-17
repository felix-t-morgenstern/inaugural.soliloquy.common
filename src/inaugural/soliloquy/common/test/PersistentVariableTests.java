package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.PersistentVariable;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import soliloquy.common.specs.IPersistentValueToWrite;

public class PersistentVariableTests extends TestCase {
	private PersistentVariable _persistentVariable;
	
	private final String PERSISTENT_VARIABLE_NAME = "PersistentVariableName";
	private final Integer PERSISTENT_VARIABLE_DEFAULT_VALUE = 123;
	private final Integer PERSISTENT_VARIABLE_ALT_VALUE = 456;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PersistentVariableTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PersistentVariableTests.class );
    }
    
	@Override
    protected void setUp() throws Exception
    {
		_persistentVariable = new PersistentVariable(PERSISTENT_VARIABLE_NAME, PERSISTENT_VARIABLE_DEFAULT_VALUE);
    }
	
	public void testGetName()
	{
		assertTrue(_persistentVariable.getName().equals(PERSISTENT_VARIABLE_NAME));
	}
	
	public void testGetValue()
	{
		assertTrue(_persistentVariable.getValue() == PERSISTENT_VARIABLE_DEFAULT_VALUE);
	}
	
	public void testSetValue()
	{
		_persistentVariable.setValue(PERSISTENT_VARIABLE_ALT_VALUE);

		assertTrue(_persistentVariable.getValue() == PERSISTENT_VARIABLE_ALT_VALUE);
	}
	
	public void testToWriteRepresentation()
	{
		IPersistentValueToWrite<Integer> persistentValueToWrite = _persistentVariable.toWriteRepresentation();

		assertTrue(persistentValueToWrite.name().equals(PERSISTENT_VARIABLE_NAME));
		assertTrue(persistentValueToWrite.value() == PERSISTENT_VARIABLE_DEFAULT_VALUE);
		assertTrue(persistentValueToWrite.typeName().equals(PERSISTENT_VARIABLE_DEFAULT_VALUE.getClass().getCanonicalName()));
		assertTrue(persistentValueToWrite.getInterfaceName()
				.equals("soliloquy.common.specs.IPersistentValueToWrite<" + PERSISTENT_VARIABLE_DEFAULT_VALUE.getClass().getCanonicalName() + ">"));
	}
}
