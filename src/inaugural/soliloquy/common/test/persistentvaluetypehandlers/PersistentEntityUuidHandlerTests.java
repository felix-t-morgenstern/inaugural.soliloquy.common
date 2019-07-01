package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentEntityUuidHandler;
import inaugural.soliloquy.common.test.stubs.EntityUuidFactoryStub;
import inaugural.soliloquy.common.test.stubs.EntityUuidStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.IEntityUuidFactory;
import soliloquy.specs.common.infrastructure.IPersistentValueTypeHandler;
import soliloquy.specs.common.valueobjects.IEntityUuid;

import static org.junit.jupiter.api.Assertions.*;

class PersistentEntityUuidHandlerTests {
    private final IEntityUuidFactory ENTITY_UUID_FACTORY = new EntityUuidFactoryStub();
    private final String ENTITY_UUID_STRING = "5ab46602-2493-4dbd-831c-6e63a6e6094b";
    private final IEntityUuid ENTITY_UUID = new EntityUuidStub(ENTITY_UUID_STRING);

    private IPersistentValueTypeHandler<IEntityUuid> _persistentEntityUuidHandler;

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
        assertEquals(IPersistentValueTypeHandler.class.getCanonicalName() + "<" +
                IEntityUuid.class.getCanonicalName() + ">",
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
}
