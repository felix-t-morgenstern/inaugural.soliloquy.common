package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import inaugural.soliloquy.common.EntityUuidFactory;
import soliloquy.common.specs.IEntityUuid;

public class EntityUuidFactoryTests {
	private EntityUuidFactory entityUuidFactory;
	
	private final String ENTITY_UUID_STRING = "64a853a4-a156-42e1-b8c2-02ddb44f5e64";
	private final long ENTITY_UUID_MOST_SIGNIFICANT_BITS = 7253139166426710753L;
	private final long ENTITY_UUID_LEAST_SIGNIFICANT_BITS = -5133537474012815772L;
	
	private final String BLANK_ENTITY_UUID_STRING = "00000000-0000-0000-0000-000000000000";

    @BeforeEach
    protected void setUp() throws Exception
    {
    	Mockito.reset();
    	entityUuidFactory = new EntityUuidFactory();
    }

    @Test
    public void testCreateFromLongs()
    {
    	IEntityUuid createdEntityUuid = entityUuidFactory.createFromLongs(ENTITY_UUID_MOST_SIGNIFICANT_BITS, ENTITY_UUID_LEAST_SIGNIFICANT_BITS);
    	assertTrue(ENTITY_UUID_MOST_SIGNIFICANT_BITS == createdEntityUuid.getMostSignificantBits());
    	assertTrue(ENTITY_UUID_LEAST_SIGNIFICANT_BITS == createdEntityUuid.getLeastSignificantBits());
    }

    @Test
    public void testCreateFromString()
    {
    	IEntityUuid createdEntityUuid = entityUuidFactory.createFromString(ENTITY_UUID_STRING);
    	assertTrue(ENTITY_UUID_STRING.equals(createdEntityUuid.toString()));
    }

    @Test
    public void testCreateRandomEntityUuid()
    {
    	IEntityUuid createdEntityUuid = entityUuidFactory.createRandomEntityUuid();
    	assertTrue(!BLANK_ENTITY_UUID_STRING.equals(createdEntityUuid.toString()));
    }
}
