package inaugural.soliloquy.common.test.persistentvaluetypehandlers;

import inaugural.soliloquy.common.persistentvaluetypehandlers.PersistentRegistryHandlerImpl;
import inaugural.soliloquy.common.test.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.PersistentRegistryHandler;
import soliloquy.specs.common.infrastructure.Registry;

import static org.junit.jupiter.api.Assertions.*;

class PersistentRegistryHandlerImplTests {
    private PersistentRegistryHandler _persistentRegistryHandler;

    private final String DATA_STRING = "{\"typeName\":\"inaugural.soliloquy.common.test.stubs.HasIdAndNameStub\",\"serializedValues\":[\"{\\\"id\\\":\\\"id2\\\",\\\"name\\\":\\\"name2\\\"}\",\"{\\\"id\\\":\\\"id1\\\",\\\"name\\\":\\\"name1\\\"}\",\"{\\\"id\\\":\\\"id3\\\",\\\"name\\\":\\\"name3\\\"}\"]}";

    @BeforeEach
    void setup() {
        _persistentRegistryHandler = new PersistentRegistryHandlerImpl(
                new PersistentValuesHandlerStub(), new RegistryFactoryStub());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentRegistryHandlerImpl(null, new RegistryFactoryStub()));
        assertThrows(IllegalArgumentException.class,
                () -> new PersistentRegistryHandlerImpl(new PersistentValuesHandlerStub(), null));
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _persistentRegistryHandler.write(null));
    }

    @Test
    void testWrite() {
        Registry<HasIdAndNameStub> registry = new RegistryStub<>(new HasIdAndNameStub("", ""));
        registry.register(new HasIdAndNameStub("id1", "name1"));
        registry.register(new HasIdAndNameStub("id2", "name2"));
        registry.register(new HasIdAndNameStub("id3", "name3"));

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
        Registry<HasIdAndNameStub> registry = _persistentRegistryHandler.read(DATA_STRING);

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
        HasIdAndNameStub archetype = (HasIdAndNameStub) _persistentRegistryHandler
                .generateArchetype(Registry.class.getCanonicalName() + "<" +
                        HasIdAndNameStub.class.getCanonicalName() + ">").getArchetype();

        assertEquals(PersistentHasIdAndNameHandler.ARCHETYPE_ID, archetype.id());
        assertEquals(PersistentHasIdAndNameHandler.ARCHETYPE_NAME, archetype.getName());
    }

    @Test
    void testGetInterfaceName() {
        assertThrows(UnsupportedOperationException.class,
                () -> _persistentRegistryHandler.getInterfaceName());
    }
}
