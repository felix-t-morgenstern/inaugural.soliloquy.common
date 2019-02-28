package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import inaugural.soliloquy.common.EntityUuid;

public class EntityUuidTests {
	private EntityUuid entityUuid;

    @BeforeEach
    protected void setUp() throws Exception
    {
    	Mockito.reset();
    	entityUuid = new EntityUuid();
    }

    @Test
    public void testUuidFromLongs()
    {
    	entityUuid = new EntityUuid(123, 456);
    	assertTrue(entityUuid.getMostSignificantBits() == 123);
    	assertTrue(entityUuid.getLeastSignificantBits() == 456);
    }

    @Test
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

    @Test
    public void testUuidFromString()
    {
    	entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	assertTrue(entityUuid.toString().equals("a1a2e5a2-8960-11e8-9a94-a6cf71072f73"));
    }

    @Test
    public void testUuidEquals()
    {
    	entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	EntityUuid otherEntityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	assertTrue(entityUuid.equals(otherEntityUuid));
    	
    	otherEntityUuid = new EntityUuid("b1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	assertTrue(!entityUuid.equals(otherEntityUuid));
    }

    @Test
    public void testUuidEqualsNullEntityUuid()
    {
		entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	EntityUuid otherEntityUuid = null;
    	
    	assertTrue(!entityUuid.equals(otherEntityUuid));
    }

    @Test
    public void testInitializeToRandomUuid()
    {
    	entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	for(int i = 0; i < 10000; i++)
    	{
    		EntityUuid otherUuid = new EntityUuid();
    		otherUuid = new EntityUuid();
    		assertTrue(!entityUuid.equals(otherUuid));
    	}
    }
}
