package inaugural.soliloquy.common.test;

import org.mockito.Mockito;

import inaugural.soliloquy.common.EntityUuid;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class EntityUuidTests extends TestCase {
	private EntityUuid entityUuid;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public EntityUuidTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( EntityUuidTests.class );
    }
    
    @Override
    protected void setUp() throws Exception
    {
    	Mockito.reset();
    	entityUuid = new EntityUuid();
    }

    public void testUuidFromLongs()
    {
    	entityUuid = new EntityUuid(123, 456);
    	assertTrue(entityUuid.getMostSignificantBits() == 123);
    	assertTrue(entityUuid.getLeastSignificantBits() == 456);
    }
    
    public void testUuidFromInvalidString()
    {
    	try 
    	{
    		entityUuid = new EntityUuid("dfghdfgh");
    	}
    	catch(IllegalArgumentException e)
    	{
    		assertTrue(true);
    	}
    	catch(Exception e)
    	{
    		assertTrue(false);
    	}
    }
    
    public void testUuidFromString()
    {
    	entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	assertTrue(entityUuid.toString().equals("a1a2e5a2-8960-11e8-9a94-a6cf71072f73"));
    }
    
    public void testUuidEquals()
    {
    	entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	EntityUuid otherEntityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	assertTrue(entityUuid.equals(otherEntityUuid));
    	
    	otherEntityUuid = new EntityUuid("b1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	assertFalse(entityUuid.equals(otherEntityUuid));
    }
    
    public void testUuidEqualsNullEntityUuid()
    {
		entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	EntityUuid otherEntityUuid = null;
    	
    	assertFalse(entityUuid.equals(otherEntityUuid));
    }
    
    public void testInitializeToRandomUuid()
    {
    	entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	for(int i = 0; i < 10000; i++)
    	{
    		EntityUuid otherUuid = new EntityUuid();
    		otherUuid = new EntityUuid();
    		assertFalse(entityUuid.equals(otherUuid));
    	}
    }
}
