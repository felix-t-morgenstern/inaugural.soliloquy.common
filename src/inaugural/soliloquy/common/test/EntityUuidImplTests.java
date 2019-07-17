package inaugural.soliloquy.common.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import inaugural.soliloquy.common.EntityUuidImpl;
import soliloquy.specs.common.valueobjects.EntityUuid;

import static org.junit.jupiter.api.Assertions.*;

class EntityUuidImplTests {
	private EntityUuidImpl _entityUuid;

    @BeforeEach
	void setUp() {
    	Mockito.reset();
    	_entityUuid = new EntityUuidImpl();
    }

    @Test
	void testUuidFromLongs() {
    	_entityUuid = new EntityUuidImpl(123, 456);
		assertEquals(123, _entityUuid.getMostSignificantBits());
		assertEquals(456, _entityUuid.getLeastSignificantBits());
    }

    @Test
	void testUuidFromInvalidString() {
    	assertThrows(IllegalArgumentException.class, () -> new EntityUuidImpl("dfghdfgh"));
    }

    @Test
	void testUuidFromString() {
    	_entityUuid = new EntityUuidImpl("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
		assertEquals("a1a2e5a2-8960-11e8-9a94-a6cf71072f73", _entityUuid.toString());
    }

    @Test
	void testUuidEquals() {
    	_entityUuid = new EntityUuidImpl("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	EntityUuidImpl otherEntityUuid = new EntityUuidImpl("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");

		assertEquals(_entityUuid, otherEntityUuid);
    	
    	otherEntityUuid = new EntityUuidImpl("b1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	assertTrue(!_entityUuid.equals(otherEntityUuid));
    }

    @Test
	void testUuidEqualsNullEntityUuid() {
    	_entityUuid = new EntityUuidImpl("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	
    	EntityUuidImpl otherEntityUuid = null;
    	
    	assertTrue(!_entityUuid.equals(otherEntityUuid));
    }

    @Test
	void testInitializeToRandomUuid() {
    	_entityUuid = new EntityUuidImpl("a1a2e5a2-8960-11e8-9a94-a6cf71072f73");
    	for(int i = 0; i < 10000; i++) {
			EntityUuidImpl otherUuid = new EntityUuidImpl();
    		assertTrue(!_entityUuid.equals(otherUuid));
    	}
    }

    @Test
	void testGetInterfaceName() {
    	assertEquals(EntityUuid.class.getCanonicalName(), _entityUuid.getInterfaceName());
	}
}
