package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import inaugural.soliloquy.common.EntityUuid;

public class EntityUuidTests {
	private EntityUuid _entityUuid;

    @BeforeEach
    protected void setUp() throws Exception {
    	Mockito.reset();
    	_entityUuid = new EntityUuid();
    }

    @Test
    public void testUuidFromLongs() {
    	_entityUuid = new EntityUuid(123, 456);
    	assertTrue(_entityUuid.getMostSignificantBits() == 123);
    	assertTrue(_entityUuid.getLeastSignificantBits() == 456);
    }

    @Test
    public void testUuidFromInvalidString() {
    	try {
    		_entityUuid = new EntityUuid("dfghdfgh");
    	} catch(IllegalArgumentException e) {
    		assertTrue(true);
    	} catch(Exception e) {
    		assertTrue(false);
    	}
    }

    @Test
    public void testUuidFromString() {
    	_entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	assertTrue(_entityUuid.toString().equals("a1a2e5a2-8960-11e8-9a94-a6cf71072f73"));
    }

    @Test
    public void testUuidEquals() {
    	_entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	EntityUuid otherEntityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	assertTrue(_entityUuid.equals(otherEntityUuid));
    	
    	otherEntityUuid = new EntityUuid("b1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	assertTrue(!_entityUuid.equals(otherEntityUuid));
    }

    @Test
    public void testUuidEqualsNullEntityUuid() {
    	_entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	EntityUuid otherEntityUuid = null;
    	
    	assertTrue(!_entityUuid.equals(otherEntityUuid));
    }

    @Test
    public void testInitializeToRandomUuid() {
    	_entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	for(int i = 0; i < 10000; i++) {
    		EntityUuid otherUuid = new EntityUuid();
    		otherUuid = new EntityUuid();
    		assertTrue(!_entityUuid.equals(otherUuid));
    	}
    }
}
