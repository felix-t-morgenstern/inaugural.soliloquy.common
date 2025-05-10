package inaugural.soliloquy.common.test.unit.factories;

import inaugural.soliloquy.common.factories.RegistryFactoryImpl;
import inaugural.soliloquy.common.infrastructure.RegistryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.shared.HasId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class RegistryFactoryImplTests {
    @Mock private HasId archetype;

    private RegistryFactory registryFactory;

    @BeforeEach
    public void setUp() {
        registryFactory = new RegistryFactoryImpl();
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(RegistryFactory.class.getCanonicalName(),
                registryFactory.getInterfaceName());
    }

    @Test
    public void testMake() {
        var registry = registryFactory.make(archetype);

        assertNotNull(registry);
        assertTrue(registry instanceof RegistryImpl);
        assertSame(archetype, registry.archetype());
    }

    @Test
    public void testMakeWithNullArchetype() {
        assertThrows(IllegalArgumentException.class, () -> registryFactory.make(null));
    }

    @Test
    public void testHashCode() {
        assertEquals(RegistryFactoryImpl.class.getCanonicalName().hashCode(),
                registryFactory.hashCode());
    }

    @Test
    public void testEquals() {
        var equalRegistryFactory = new RegistryFactoryImpl();
        var unequalRegistryFactory = mock(RegistryFactory.class);

        assertEquals(equalRegistryFactory, registryFactory);
        assertNotEquals(unequalRegistryFactory, registryFactory);
        assertNotEquals(null, registryFactory);
    }

    @Test
    public void testToString() {
        assertEquals(RegistryFactoryImpl.class.getCanonicalName(),
                registryFactory.toString());
    }
}
