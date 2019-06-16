package inaugural.soliloquy.common.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import inaugural.soliloquy.common.EntityUuid;
import soliloquy.specs.common.valueobjects.IEntityUuid;

import static org.junit.jupiter.api.Assertions.*;

class EntityUuidTests {
	private EntityUuid _entityUuid;

    @BeforeEach
	void setUp() {
    	Mockito.reset();
    	_entityUuid = new EntityUuid();
    }

    @Test
	void testUuidFromLongs() {
    	_entityUuid = new EntityUuid(123, 456);
		assertEquals(123, _entityUuid.getMostSignificantBits());
		assertEquals(456, _entityUuid.getLeastSignificantBits());
    }

    @Test
	void testUuidFromInvalidString() {
    	assertThrows(IllegalArgumentException.class, () -> new EntityUuid("dfghdfgh"));
    }

    @Test
	void testUuidFromString() {
    	_entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
		assertEquals("a1a2e5a2-8960-11e8-9a94-a6cf71072f73", _entityUuid.toString());
    }

    @Test
	void testUuidEquals() {
    	_entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	EntityUuid otherEntityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");

		assertEquals(_entityUuid, otherEntityUuid);
    	
    	otherEntityUuid = new EntityUuid("b1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	assertTrue(!_entityUuid.equals(otherEntityUuid));
    }

    @Test
	void testUuidEqualsNullEntityUuid() {
    	_entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	EntityUuid otherEntityUuid = null;
    	
    	assertTrue(!_entityUuid.equals(otherEntityUuid));
    }

    @Test
	void testInitializeToRandomUuid() {
    	_entityUuid = new EntityUuid("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	for(int i = 0; i < 10000; i++) {
			EntityUuid otherUuid = new EntityUuid();
    		assertTrue(!_entityUuid.equals(otherUuid));
    	}
    }

    @Test
	void testGetInterfaceName() {
    	assertEquals(IEntityUuid.class.getCanonicalName(), _entityUuid.getInterfaceName());
	}
}
