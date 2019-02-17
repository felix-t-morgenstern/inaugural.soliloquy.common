package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.PersistentVariableFactory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import soliloquy.common.specs.IPersistentVariable;

public class PersistentVariableFactoryTests extends TestCase {
	private final String PERSISTENT_VARIABLE_NAME = "persistentVariableName";
	private final Double PERSISTENT_VARIABLE_VALUE = 1.23;
	
	private PersistentVariableFactory _persistentVariableFactory;
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PersistentVariableFactoryTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PersistentVariableFactoryTests.class );
    }
    
	@Override
    protected void setUp() throws Exception
    {
		_persistentVariableFactory = new PersistentVariableFactory();
    }
	
	public void testMake()
	{
		IPersistentVariable persistentVariable = _persistentVariableFactory.make(PERSISTENT_VARIABLE_NAME, PERSISTENT_VARIABLE_VALUE);
		
		assertTrue(persistentVariable != null);
		assertTrue(persistentVariable instanceof IPersistentVariable);
		assertTrue(persistentVariable.getName().equals(PERSISTENT_VARIABLE_NAME));
		assertTrue(persistentVariable.getValue() == PERSISTENT_VARIABLE_VALUE);
	}
}
