package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.EntityUuidHandler;
import inaugural.soliloquy.common.test.fakes.FakeEntityUuid;
import inaugural.soliloquy.common.test.fakes.FakeEntityUuidFactory;
import inaugural.soliloquy.common.test.fakes.FakeEntityUuidHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.EntityUuidFactory;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.valueobjects.EntityUuid;

import static org.junit.jupiter.api.Assertions.*;

class EntityUuidHandlerTests {
    private final EntityUuidFactory ENTITY_UUID_FACTORY = new FakeEntityUuidFactory();
    private final String ENTITY_UUID_STRING = "5ab46602-2493-4dbd-831c-6e63a6e6094b";
    private final EntityUuid ENTITY_UUID = new FakeEntityUuid(ENTITY_UUID_STRING);

    private TypeHandler<EntityUuid> _entityUuidHandler;

    @BeforeEach
    void setUp() {
        _entityUuidHandler = new EntityUuidHandler(ENTITY_UUID_FACTORY);
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_entityUuidHandler.getArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                EntityUuid.class.getCanonicalName() + ">",
                _entityUuidHandler.getInterfaceName());
    }

    @Test
    void testRead() {
        assertEquals(ENTITY_UUID, _entityUuidHandler.read(ENTITY_UUID_STRING));
    }

    @Test
    void testReadNull() {
        assertNull(_entityUuidHandler.read(""));
    }

    @Test
    void testWrite() {
        assertEquals(ENTITY_UUID_STRING, _entityUuidHandler.write(ENTITY_UUID));
    }

    @Test
    void testWriteNull() {
        assertEquals("", _entityUuidHandler.write(null));
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        EntityUuid.class.getCanonicalName() + ">").hashCode(),
                _entityUuidHandler.hashCode());
    }

    @Test
    void testEquals() {
        TypeHandler<EntityUuid> equalHandler = new EntityUuidHandler(ENTITY_UUID_FACTORY);
        TypeHandler<EntityUuid> unequalHandler = new FakeEntityUuidHandler();

        assertEquals(_entityUuidHandler, equalHandler);
        assertNotEquals(_entityUuidHandler, unequalHandler);
        assertNotEquals(null, _entityUuidHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        EntityUuid.class.getCanonicalName() + ">",
                _entityUuidHandler.toString());
    }
}
