package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PersistentEntityUuidHandler;
import inaugural.soliloquy.common.test.fakes.FakeEntityUuid;
import inaugural.soliloquy.common.test.fakes.FakeEntityUuidFactory;
import inaugural.soliloquy.common.test.fakes.FakePersistentEntityUuidHandler;
import inaugural.soliloquy.tools.persistence.PersistentTypeHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.persistence.PersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.EntityUuid;

import static org.junit.jupiter.api.Assertions.*;

class PersistentEntityUuidHandlerTests {
    private final EntityUuidFactory ENTITY_UUID_FACTORY = new FakeEntityUuidFactory();
    private final String ENTITY_UUID_STRING = "5ab46602-2493-4dbd-831c-6e63a6e6094b";
    private final EntityUuid ENTITY_UUID = new FakeEntityUuid(ENTITY_UUID_STRING);

    private PersistentValueTypeHandler<EntityUuid> _persistentEntityUuidHandler;

    @BeforeEach
    void setUp() {
        _persistentEntityUuidHandler = new PersistentEntityUuidHandler(ENTITY_UUID_FACTORY);
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_persistentEntityUuidHandler.getArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                EntityUuid.class.getCanonicalName() + ">",
                _persistentEntityUuidHandler.getInterfaceName());
    }

    @Test
    void testRead() {
        assertEquals(ENTITY_UUID, _persistentEntityUuidHandler.read(ENTITY_UUID_STRING));
    }

    @Test
    void testReadNull() {
        assertNull(_persistentEntityUuidHandler.read(""));
    }

    @Test
    void testWrite() {
        assertEquals(ENTITY_UUID_STRING, _persistentEntityUuidHandler.write(ENTITY_UUID));
    }

    @Test
    void testWriteNull() {
        assertEquals("", _persistentEntityUuidHandler.write(null));
    }

    @Test
    void testHashCode() {
        assertEquals((PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        EntityUuid.class.getCanonicalName() + ">").hashCode(),
                _persistentEntityUuidHandler.hashCode());
    }

    @Test
    void testEquals() {
        PersistentTypeHandler<EntityUuid> equalHandler =
                new PersistentEntityUuidHandler(ENTITY_UUID_FACTORY);
        PersistentValueTypeHandler<EntityUuid> unequalHandler =
                new FakePersistentEntityUuidHandler();

        assertEquals(_persistentEntityUuidHandler, equalHandler);
        assertNotEquals(_persistentEntityUuidHandler, unequalHandler);
        assertNotEquals(null, _persistentEntityUuidHandler);
    }

    @Test
    void testToString() {
        assertEquals(PersistentValueTypeHandler.class.getCanonicalName() + "<" +
                        EntityUuid.class.getCanonicalName() + ">",
                _persistentEntityUuidHandler.toString());
    }
}
