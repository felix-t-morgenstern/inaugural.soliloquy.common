package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.RegistryFactoryImpl;
import inaugural.soliloquy.common.test.stubs.HasIdAndNameStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;

import static org.junit.jupiter.api.Assertions.*;

class RegistryFactoryImplTests {
    private static String ID = "id";
    private static String NAME = "name";
    private static HasIdAndNameStub HAS_ID_AND_NAME = new HasIdAndNameStub(ID, NAME);

    private RegistryFactory _registryFactory;

    @BeforeEach
    void setUp() {
        _registryFactory = new RegistryFactoryImpl();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RegistryFactory.class.getCanonicalName(),
                _registryFactory.getInterfaceName());
    }

    @Test
    void testMake() {
        Registry<HasIdAndNameStub> registry = _registryFactory.make(HAS_ID_AND_NAME);

        assertNotNull(registry);
        HasIdAndNameStub archetype = registry.getArchetype();
        assertNotNull(archetype);
        assertEquals(HasIdAndNameStub.class.getCanonicalName(), archetype.getInterfaceName());
    }

    @Test
    void testMakeWithNullArchetype() {
        assertThrows(IllegalArgumentException.class, () -> _registryFactory.make(null));
    }
}
