package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.RegistryFactoryImpl;
import inaugural.soliloquy.common.test.fakes.FakeCollectionFactory;
import inaugural.soliloquy.common.test.fakes.FakeHasIdAndName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.RegistryFactory;
import soliloquy.specs.common.infrastructure.Registry;

import static org.junit.jupiter.api.Assertions.*;

class RegistryFactoryImplTests {
    private static CollectionFactory COLLECTION_FACTORY = new FakeCollectionFactory();
    private static String ID = "id";
    private static String NAME = "name";
    private static FakeHasIdAndName HAS_ID_AND_NAME = new FakeHasIdAndName(ID, NAME);

    private RegistryFactory _registryFactory;

    @BeforeEach
    void setUp() {
        _registryFactory = new RegistryFactoryImpl(COLLECTION_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new RegistryFactoryImpl(null));
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
}
