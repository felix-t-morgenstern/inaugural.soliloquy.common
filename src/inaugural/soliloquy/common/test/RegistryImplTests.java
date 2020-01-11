package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.RegistryImpl;
import inaugural.soliloquy.common.test.stubs.HasIdAndNameStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Registry;

import static org.junit.jupiter.api.Assertions.*;

class RegistryImplTests {
    private static HasIdAndNameStub ARCHETYPE = new HasIdAndNameStub("", "");
    private static String ID = "id";
    private static String NAME = "name";

    private Registry<HasIdAndNameStub> _registry;
    private HasIdAndNameStub _hasIdAndStub = new HasIdAndNameStub(ID, NAME);

    @BeforeEach
    void setUp() {
        _registry = new RegistryImpl<>(ARCHETYPE);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _registry = new RegistryImpl<>(null));
    }

    @Test
    void testArchetype() {
        assertSame(ARCHETYPE, _registry.getArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Registry.class.getCanonicalName() + "<" +
                HasIdAndNameStub.class.getCanonicalName() + ">",
                _registry.getInterfaceName());
    }

    @Test
    void testAddAndGet() {
        _registry.add(_hasIdAndStub);

        assertSame(_hasIdAndStub, _registry.get(ID));
    }

    @Test
    void testAddNull() {
        assertThrows(IllegalArgumentException.class, () -> _registry.add(null));
    }

    @Test
    void testAddWithInvalidIds() {
        assertThrows(IllegalArgumentException.class,
                () -> _registry.add(new HasIdAndNameStub(null, NAME)));
        assertThrows(IllegalArgumentException.class,
                () -> _registry.add(new HasIdAndNameStub("", NAME)));
    }

    @Test
    void testAddAllFromCollection() {
        fail("");
    }

    @Test
    void testAddAllFromCollectionWithNull() {
        fail("");
    }

    @Test
    void testAddAllFromCollectionWithInvalidEntries() {
        fail("");
    }

    @Test
    void testAddAllFromArray() {
        fail("");
    }

    @Test
    void testAddAllFromArrayWithNull() {
        fail("");
    }

    @Test
    void testAddAllFromArrayWithInvalidEntries() {
        fail("");
    }

    @Test
    void testClear() {
        fail("");
    }

    @Test
    void testContains() {
        assertFalse(_registry.contains(ID));

        _registry.add(_hasIdAndStub);

        assertTrue(_registry.contains(ID));
    }

    @Test
    void testRemoveById() {
        assertFalse(_registry.contains(ID));

        _registry.add(_hasIdAndStub);

        assertTrue(_registry.remove(ID));
        assertFalse(_registry.remove(ID));
        assertFalse(_registry.contains(ID));
    }

    @Test
    void testRemoveByItem() {
        fail("");
    }

    @Test
    void testSize() {
        _registry.add(new HasIdAndNameStub("id1", "name1"));
        _registry.add(new HasIdAndNameStub("id2", "name2"));
        _registry.add(new HasIdAndNameStub("id3", "name3"));

        assertEquals(3, _registry.size());
    }

    @Test
    void testIterator() {
        _registry.add(new HasIdAndNameStub("id1", "name1"));
        _registry.add(new HasIdAndNameStub("id2", "name2"));
        _registry.add(new HasIdAndNameStub("id3", "name3"));

        for (HasIdAndNameStub hasIdAndNameStub : _registry) {
            switch (hasIdAndNameStub.id()) {
                case "id1":
                    assertEquals("name1", hasIdAndNameStub.getName());
                    break;
                case "id2":
                    assertEquals("name2", hasIdAndNameStub.getName());
                    break;
                case "id3":
                    assertEquals("name3", hasIdAndNameStub.getName());
                    break;
                default:
                    fail("Invalid id");
            }
        }
    }
}
