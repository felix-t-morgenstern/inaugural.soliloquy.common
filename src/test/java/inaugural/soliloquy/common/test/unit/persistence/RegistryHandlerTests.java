package inaugural.soliloquy.common.test.unit.persistence;

import inaugural.soliloquy.common.test.fakes.FakeHasIdAndName;
import inaugural.soliloquy.common.persistence.RegistryHandler;
import inaugural.soliloquy.common.test.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;

import static org.junit.jupiter.api.Assertions.*;

class RegistryHandlerTests {
    private final PersistentValuesHandler PERSISTENT_VALUES_HANDLER =
            new FakePersistentValuesHandler();
    private final RegistryFactory REGISTRY_FACTORY = new FakeRegistryFactory();

    private RegistryHandler _registryHandler;

    private final String DATA_STRING =
            "{\"typeName\":\"inaugural.soliloquy.common.test.fakes.FakeHasIdAndName\"," +
                    "\"serializedValues\":[\"{\\\"id\\\":\\\"id2\\\"," +
                    "\\\"name\\\":\\\"name2\\\"}\",\"{\\\"id\\\":\\\"id1\\\"," +
                    "\\\"name\\\":\\\"name1\\\"}\",\"{\\\"id\\\":\\\"id3\\\"," +
                    "\\\"name\\\":\\\"name3\\\"}\"]}";

    @BeforeEach
    void setup() {
        _registryHandler = new RegistryHandler(PERSISTENT_VALUES_HANDLER,
                REGISTRY_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new RegistryHandler(null, REGISTRY_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new RegistryHandler(PERSISTENT_VALUES_HANDLER, null));
    }

    @Test
    void testWriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _registryHandler.write(null));
    }

    @Test
    void testWrite() {
        Registry<FakeHasIdAndName> registry = new FakeRegistry<>(new FakeHasIdAndName("", ""));
        registry.add(new FakeHasIdAndName("id1", "name1"));
        registry.add(new FakeHasIdAndName("id2", "name2"));
        registry.add(new FakeHasIdAndName("id3", "name3"));

        String writeOutput = _registryHandler.write(registry);

        assertEquals(DATA_STRING, writeOutput);
    }

    @Test
    void testReadWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _registryHandler.read(null));
        assertThrows(IllegalArgumentException.class, () -> _registryHandler.read(""));
    }

    @SuppressWarnings("unchecked")
    @Test
    void testRead() {
        Registry<FakeHasIdAndName> registry = _registryHandler.read(DATA_STRING);

        assertNotNull(registry);
        assertEquals(3, registry.size());
        assertEquals("name1", registry.get("id1").getName());
        assertEquals("name2", registry.get("id2").getName());
        assertEquals("name3", registry.get("id3").getName());
    }

    @Test
    void testGenerateArchetypeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _registryHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class, () ->
                _registryHandler.generateArchetype(""));
    }

    @Test
    void testGenerateArchetype() {
        //noinspection unchecked
        Registry<FakeHasIdAndName> generatedArchetype =
                _registryHandler.generateArchetype(FakeHasIdAndName.class.getCanonicalName());

        assertNotNull(generatedArchetype);
        assertNotNull(generatedArchetype.getArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Registry.class.getCanonicalName() + ">",
                _registryHandler.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertNotNull(_registryHandler.getArchetype());
        assertEquals(Registry.class.getCanonicalName(),
                _registryHandler.getArchetype().getInterfaceName());
    }

    @Test
    void testHashCode() {
        assertEquals((TypeHandler.class.getCanonicalName() + "<" +
                        Registry.class.getCanonicalName() + ">").hashCode(),
                _registryHandler.hashCode());
    }

    @Test
    void testEquals() {
        //noinspection rawtypes
        TypeHandler<Registry> equalHandler =
                new RegistryHandler(PERSISTENT_VALUES_HANDLER, REGISTRY_FACTORY);
        //noinspection rawtypes
        TypeHandler<Registry> unequalHandler = new FakeRegistryHandler();

        assertEquals(_registryHandler, equalHandler);
        assertNotEquals(_registryHandler, unequalHandler);
        assertNotEquals(null, _registryHandler);
    }

    @Test
    void testToString() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                        Registry.class.getCanonicalName() + ">",
                _registryHandler.toString());
    }
}
