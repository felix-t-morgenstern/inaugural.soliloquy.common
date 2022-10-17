package inaugural.soliloquy.common.test.unit.factories;

import inaugural.soliloquy.common.factories.RegistryFactoryImpl;
import inaugural.soliloquy.common.test.fakes.FakeHasIdAndName;
import inaugural.soliloquy.common.test.fakes.FakeRegistryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;

import static org.junit.jupiter.api.Assertions.*;

class RegistryFactoryImplTests {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final FakeHasIdAndName HAS_ID_AND_NAME = new FakeHasIdAndName(ID, NAME);

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
        Registry<FakeHasIdAndName> registry = _registryFactory.make(HAS_ID_AND_NAME);

        assertNotNull(registry);
        FakeHasIdAndName archetype = registry.getArchetype();
        assertNotNull(archetype);
        assertEquals(FakeHasIdAndName.class.getCanonicalName(), archetype.getInterfaceName());
    }

    @Test
    void testMakeWithNullArchetype() {
        assertThrows(IllegalArgumentException.class, () -> _registryFactory.make(null));
    }

    @Test
    void testHashCode() {
        assertEquals(RegistryFactoryImpl.class.getCanonicalName().hashCode(),
                _registryFactory.hashCode());
    }

    @Test
    void testEquals() {
        RegistryFactory equalRegistryFactory = new RegistryFactoryImpl();
        RegistryFactory unequalRegistryFactory = new FakeRegistryFactory();

        assertEquals(equalRegistryFactory, _registryFactory);
        assertNotEquals(unequalRegistryFactory, _registryFactory);
        assertNotEquals(null, _registryFactory);
    }

    @Test
    void testToString() {
        assertEquals(RegistryFactoryImpl.class.getCanonicalName(),
                _registryFactory.toString());
    }
}