package inaugural.soliloquy.common.test.unit.factories;

import inaugural.soliloquy.common.factories.EntityUuidFactoryImpl;
import inaugural.soliloquy.common.test.fakes.FakeEntityUuidFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.valueobjects.EntityUuid;

import static org.junit.jupiter.api.Assertions.*;

class EntityUuidFactoryImplTests {
    private EntityUuidFactoryImpl _entityUuidFactory;

    private final String ENTITY_UUID_STRING = "64a853a4-a156-42e1-b8c2-02ddb44f5e64";
    private final long ENTITY_UUID_MOST_SIGNIFICANT_BITS = 7253139166426710753L;
    private final long ENTITY_UUID_LEAST_SIGNIFICANT_BITS = -5133537474012815772L;

    private final String BLANK_ENTITY_UUID_STRING = "00000000-0000-0000-0000-000000000000";

    @BeforeEach
    void setUp() {
        Mockito.reset();
        _entityUuidFactory = new EntityUuidFactoryImpl();
    }

    @Test
    void testCreateFromLongs() {
        EntityUuid createdEntityUuid =
                _entityUuidFactory.createFromLongs(ENTITY_UUID_MOST_SIGNIFICANT_BITS,
                        ENTITY_UUID_LEAST_SIGNIFICANT_BITS);
        assertEquals(ENTITY_UUID_MOST_SIGNIFICANT_BITS,
                createdEntityUuid.getMostSignificantBits());
        assertEquals(ENTITY_UUID_LEAST_SIGNIFICANT_BITS,
                createdEntityUuid.getLeastSignificantBits());
    }

    @Test
    void testCreateFromString() {
        EntityUuid createdEntityUuid = _entityUuidFactory.createFromString(ENTITY_UUID_STRING);
        assertEquals(ENTITY_UUID_STRING, createdEntityUuid.toString());
    }

    @Test
    void testCreateRandomEntityUuid() {
        EntityUuid createdEntityUuid = _entityUuidFactory.createRandomEntityUuid();
        assertTrue(!BLANK_ENTITY_UUID_STRING.equals(createdEntityUuid.toString()));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(EntityUuidFactory.class.getCanonicalName(),
                _entityUuidFactory.getInterfaceName());
    }

    @Test
    void testHashCode() {
        assertEquals(EntityUuidFactoryImpl.class.getCanonicalName().hashCode(),
                _entityUuidFactory.hashCode());
    }

    @SuppressWarnings({"SimplifiableJUnitAssertion", "ConstantConditions"})
    @Test
    void testEquals() {
        EntityUuidFactory equalEntityUuidFactory = new EntityUuidFactoryImpl();
        EntityUuidFactory unequalEntityUuidFactory = new FakeEntityUuidFactory();

        assertTrue(_entityUuidFactory.equals(equalEntityUuidFactory));
        assertFalse(_entityUuidFactory.equals(unequalEntityUuidFactory));
        assertFalse(_entityUuidFactory.equals(null));
    }

    @Test
    void testToString() {
        assertEquals(EntityUuidFactoryImpl.class.getCanonicalName(),
                _entityUuidFactory.toString());
    }
}
