package inaugural.soliloquy.common.test.unit.valueobjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import inaugural.soliloquy.common.valueobjects.EntityUuidImpl;
import soliloquy.specs.common.valueobjects.EntityUuid;

import static org.junit.jupiter.api.Assertions.*;

class EntityUuidImplTests {
    private final String UUID_STRING = "a1a2e5a2-8960-11e8-9a94-a6cf71072f73";
    private final String UUID_STRING_2 = "b1a2e5a2-8960-11e8-9a94-a6cf71072f73";

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
        _entityUuid = new EntityUuidImpl(UUID_STRING);
        assertEquals(UUID_STRING, _entityUuid.toString());
    }

    @Test
    void testUuidFromStringWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new EntityUuidImpl(""));
    }

    @Test
    void testUuidEquals() {
        _entityUuid = new EntityUuidImpl(UUID_STRING);

        EntityUuidImpl otherEntityUuid = new EntityUuidImpl(UUID_STRING);

        assertEquals(_entityUuid, otherEntityUuid);

        otherEntityUuid = new EntityUuidImpl(UUID_STRING_2);

        assertNotEquals(_entityUuid, otherEntityUuid);
    }

    @Test
    void testUuidEqualsNullEntityUuid() {
        _entityUuid = new EntityUuidImpl(UUID_STRING);

        EntityUuidImpl otherEntityUuid = null;

        //noinspection ConstantConditions
        assertNotEquals(_entityUuid, otherEntityUuid);
    }

    @Test
    void testInitializeToRandomUuid() {
        _entityUuid = new EntityUuidImpl(UUID_STRING);
        for(int i = 0; i < 10000; i++) {
            EntityUuidImpl otherEntityUuid = new EntityUuidImpl(UUID_STRING_2);

            assertNotEquals(_entityUuid, otherEntityUuid);
        }
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(EntityUuid.class.getCanonicalName(), _entityUuid.getInterfaceName());
    }

    @Test
    void testToString() {
        _entityUuid = new EntityUuidImpl(UUID_STRING);
        assertEquals(UUID_STRING, _entityUuid.toString());
    }

    @Test
    void testHashCode() {
        final int expectedHashCode = -1018069483;

        _entityUuid = new EntityUuidImpl(UUID_STRING);

        assertEquals(expectedHashCode, _entityUuid.hashCode());
    }
}
