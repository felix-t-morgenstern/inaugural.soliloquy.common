package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.persistence.PersistentRegistryHandlerImpl;
import inaugural.soliloquy.common.test.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.persistence.PersistentRegistryHandler;
import soliloquy.specs.common.persistence.PersistentValuesHandler;

import static org.junit.jupiter.api.Assertions.*;

class PersistentRegistryHandlerImplTests {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new FakePersistentValuesHandler();
    private final RegistryFactory REGISTRY_FACTORY = new FakeRegistryFactory();

    private PersistentRegistryHandler _persistentRegistryHandler;

    private final String DATA_STRING = "{\"typeName\":\"inaugural.soliloquy.common.test.fakes.FakeHasIdAndName\",\"serializedValues\":[\"{\\\"id\\\":\\\"id2\\\",\\\"name\\\":\\\"name2\\\"}\",\"{\\\"id\\\":\\\"id1\\\",\\\"name\\\":\\\"name1\\\"}\",\"{\\\"id\\\":\\\"id3\\\",\\\"name\\\":\\\"name3\\\"}\"]}";

    @BeforeEach
    void setup() {
        _persistentRegistryHandler = new PersistentRegistryHandlerImpl(PERSISTENT_VALUES_HANDLER,
                REGISTRY_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentRegistryHandlerImpl(null, REGISTRY_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentRegistryHandlerImpl(PERSISTENT_VALUES_HANDLER, null));
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _persistentRegistryHandler.write(null));
    }

    @Test
    void testWrite() {
        Registry<FakeHasIdAndName> registry = new FakeRegistry<>(new FakeHasIdAndName("", ""));
        registry.add(new FakeHasIdAndName("id1", "name1"));
        registry.add(new FakeHasIdAndName("id2", "name2"));
        registry.add(new FakeHasIdAndName("id3", "name3"));

        String writeOutput = _persistentRegistryHandler.write(registry);

        assertEquals(DATA_STRING, writeOutput);
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _persistentRegistryHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> _persistentRegistryHandler.read(""));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRead() {
        Registry<FakeHasIdAndName> registry = _persistentRegistryHandler.read(DATA_STRING);

        assertNotNull(registry);
        assertEquals(3, registry.size());
        assertEquals("name1", registry.get("id1").getName());
        assertEquals("name2", registry.get("id2").getName());
        assertEquals("name3", registry.get("id3").getName());
    }

    @Test
    void testGenerateArchetypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _persistentRegistryHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class,
                () -> _persistentRegistryHandler.generateArchetype(""));
    }

    @Test
    void testGenerateArchetype() {
        FakeHasIdAndName archetype = (FakeHasIdAndName) _persistentRegistryHandler
                .generateArchetype(Registry.class.getCanonicalName() + "<" +
                        FakeHasIdAndName.class.getCanonicalName() + ">").getArchetype();

        assertEquals(PersistentHasIdAndNameHandler.ARCHETYPE_ID, archetype.id());
        assertEquals(PersistentHasIdAndNameHandler.ARCHETYPE_NAME, archetype.getName());
    }

    @Test
    void testGetInterfaceName() {
        assertThrows(UnsupportedOperationException.class,
                () -> _persistentRegistryHandler.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertThrows(UnsupportedOperationException.class,
                () -> _persistentRegistryHandler.getArchetype());
    }

    @Test
    void testHashCode() {
        assertEquals(PersistentRegistryHandlerImpl.class.getCanonicalName().hashCode(),
                _persistentRegistryHandler.hashCode());
    }

    @Test
    void testEquals() {
        PersistentRegistryHandler equalHandler =
                new PersistentRegistryHandlerImpl(PERSISTENT_VALUES_HANDLER, REGISTRY_FACTORY);
        PersistentRegistryHandler unequalHandler =
                new FakePersistentRegistryHandler();

        assertEquals(_persistentRegistryHandler, equalHandler);
        assertNotEquals(_persistentRegistryHandler, unequalHandler);
        assertNotEquals(null, _persistentRegistryHandler);
    }

    @Test
    void testToString() {
        assertEquals(PersistentRegistryHandlerImpl.class.getCanonicalName(),
                _persistentRegistryHandler.toString());
    }
}
