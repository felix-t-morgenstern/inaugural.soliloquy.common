package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import inaugural.soliloquy.common.EntityUuidFactory;
import soliloquy.specs.common.factories.IEntityUuidFactory;
import soliloquy.specs.common.valueobjects.IEntityUuid;

class EntityUuidFactoryTests {
	private EntityUuidFactory _entityUuidFactory;
	
	private final String ENTITY_UUID_STRING = "64a853a4-a156-42e1-b8c2-02ddb44f5e64";
	private final long ENTITY_UUID_MOST_SIGNIFICANT_BITS = 7253139166426710753L;
	private final long ENTITY_UUID_LEAST_SIGNIFICANT_BITS = -5133537474012815772L;
	
	private final String BLANK_ENTITY_UUID_STRING = "00000000-0000-0000-0000-000000000000";

    @BeforeEach
	void setUp() {
    	Mockito.reset();
    	_entityUuidFactory = new EntityUuidFactory();
    }

    @Test
	void testCreateFromLongs() {
    	IEntityUuid createdEntityUuid =
    			_entityUuidFactory.createFromLongs(ENTITY_UUID_MOST_SIGNIFICANT_BITS,
						ENTITY_UUID_LEAST_SIGNIFICANT_BITS);
		assertEquals(ENTITY_UUID_MOST_SIGNIFICANT_BITS,
				createdEntityUuid.getMostSignificantBits());
		assertEquals(ENTITY_UUID_LEAST_SIGNIFICANT_BITS,
				createdEntityUuid.getLeastSignificantBits());
    }

    @Test
	void testCreateFromString() {
    	IEntityUuid createdEntityUuid = _entityUuidFactory.createFromString(ENTITY_UUID_STRING);
		assertEquals(ENTITY_UUID_STRING, createdEntityUuid.toString());
    }

    @Test
	void testCreateRandomEntityUuid() {
    	IEntityUuid createdEntityUuid = _entityUuidFactory.createRandomEntityUuid();
    	assertTrue(!BLANK_ENTITY_UUID_STRING.equals(createdEntityUuid.toString()));
    }

    @Test
	void testGetInterfaceName() {
    	assertEquals(IEntityUuidFactory.class.getCanonicalName(),
				_entityUuidFactory.getInterfaceName());
	}
}
