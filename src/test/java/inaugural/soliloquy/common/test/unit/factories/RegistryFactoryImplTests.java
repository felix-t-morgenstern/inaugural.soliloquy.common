package inaugural.soliloquy.common.test.unit.factories;

import inaugural.soliloquy.common.factories.RegistryFactoryImpl;
import inaugural.soliloquy.common.infrastructure.RegistryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegistryFactoryImplTests {
    @Mock private HasId archetype;

    private RegistryFactory registryFactory;

    @BeforeEach
    void setUp() {
        archetype = mock(HasId.class);
        when(archetype.id()).thenReturn(randomString());

        registryFactory = new RegistryFactoryImpl();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RegistryFactory.class.getCanonicalName(),
                registryFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        Registry<HasId> registry = registryFactory.make(archetype);

        assertNotNull(registry);
        assertTrue(registry instanceof RegistryImpl);
        assertSame(archetype, registry.getArchetype());
    }

    @Test
    void testMakeWithNullArchetype() {
        assertThrows(IllegalArgumentException.class, () -> registryFactory.make(null));
    }

    @Test
    void testHashCode() {
        assertEquals(RegistryFactoryImpl.class.getCanonicalName().hashCode(),
                registryFactory.hashCode());
    }

    @Test
    void testEquals() {
        RegistryFactory equalRegistryFactory = new RegistryFactoryImpl();
        RegistryFactory unequalRegistryFactory = mock(RegistryFactory.class);

        assertEquals(equalRegistryFactory, registryFactory);
        assertNotEquals(unequalRegistryFactory, registryFactory);
        assertNotEquals(null, registryFactory);
    }

    @Test
    void testToString() {
        assertEquals(RegistryFactoryImpl.class.getCanonicalName(),
                registryFactory.toString());
    }
}
