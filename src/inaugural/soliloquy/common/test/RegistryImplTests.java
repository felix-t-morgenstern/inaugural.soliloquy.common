package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.RegistryImpl;
import inaugural.soliloquy.common.test.stubs.CollectionFactoryStub;
import inaugural.soliloquy.common.test.stubs.CollectionStub;
import inaugural.soliloquy.common.test.stubs.HasIdAndNameStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.Registry;

import static org.junit.jupiter.api.Assertions.*;

class RegistryImplTests {
    private static HasIdAndNameStub ARCHETYPE = new HasIdAndNameStub("", "");
    private static CollectionFactory COLLECTION_FACTORY = new CollectionFactoryStub();
    private static String ID = "id";
    private static String NAME = "name";

    private Registry<HasIdAndNameStub> _registry;
    private HasIdAndNameStub _hasIdAndStub = new HasIdAndNameStub(ID, NAME);

    @BeforeEach
    void setUp() {
        _registry = new RegistryImpl<>(ARCHETYPE, COLLECTION_FACTORY);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _registry = new RegistryImpl<>(null, COLLECTION_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> _registry = new RegistryImpl<>(ARCHETYPE, null));
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
    void testContainsItem() {
        _registry.add(_hasIdAndStub);

        assertTrue(_registry.contains(_hasIdAndStub));
    }

    @Test
    void testContainsId() {
        assertFalse(_registry.contains(ID));

        _registry.add(_hasIdAndStub);

        assertTrue(_registry.contains(ID));
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
        HasIdAndNameStub item1 = new HasIdAndNameStub("id1", "name1");
        HasIdAndNameStub item2 = new HasIdAndNameStub("id2", "name2");
        HasIdAndNameStub item3 = new HasIdAndNameStub("id3", "name3");
        Collection<HasIdAndNameStub> toAdd = new CollectionStub<>(null);
        toAdd.add(item1);
        toAdd.add(item2);
        toAdd.add(item3);

        _registry.addAll(toAdd);

        assertTrue(_registry.contains(item1));
        assertTrue(_registry.contains(item2));
        assertTrue(_registry.contains(item3));
    }

    @Test
    void testAddAllFromCollectionWithNull() {
        assertThrows(IllegalArgumentException.class,
                () -> _registry.addAll((ReadableCollection<HasIdAndNameStub>)null));
    }

    @Test
    void testAddAllFromCollectionWithInvalidEntries() {
        Collection<HasIdAndNameStub> collectionWithNull = new CollectionStub<>();
        collectionWithNull.add(null);

        Collection<HasIdAndNameStub> collectionWithNullId = new CollectionStub<>();
        collectionWithNullId.add(new HasIdAndNameStub(null, "name"));

        Collection<HasIdAndNameStub> collectionWithEmptyId = new CollectionStub<>();
        collectionWithEmptyId.add(new HasIdAndNameStub("", "name"));

        assertThrows(IllegalArgumentException.class, () -> _registry.addAll(collectionWithNull));
        assertThrows(IllegalArgumentException.class, () -> _registry.addAll(collectionWithNullId));
        assertThrows(IllegalArgumentException.class,
                () -> _registry.addAll(collectionWithEmptyId));
    }

    @Test
    void testAddAllFromArray() {
        HasIdAndNameStub item1 = new HasIdAndNameStub("id1", "name1");
        HasIdAndNameStub item2 = new HasIdAndNameStub("id2", "name2");
        HasIdAndNameStub item3 = new HasIdAndNameStub("id3", "name3");
        HasIdAndNameStub[] array = new HasIdAndNameStub[3];
        array[0] = item1;
        array[1] = item2;
        array[2] = item3;

        _registry.addAll(array);

        assertTrue(_registry.contains(item1));
        assertTrue(_registry.contains(item2));
        assertTrue(_registry.contains(item3));
    }

    @Test
    void testAddAllFromArrayWithNull() {
        assertThrows(IllegalArgumentException.class,
                () -> _registry.addAll((HasIdAndNameStub[])null));
    }

    @Test
    void testAddAllFromArrayWithInvalidEntries() {
        HasIdAndNameStub[] arrayWithNull = new HasIdAndNameStub[1];
        arrayWithNull[0] = null;

        HasIdAndNameStub[] arrayWithNullId = new HasIdAndNameStub[1];
        arrayWithNullId[0] = new HasIdAndNameStub(null, "name");

        HasIdAndNameStub[] arrayWithEmptyId = new HasIdAndNameStub[1];
        arrayWithEmptyId[0] = new HasIdAndNameStub("", "name");

        assertThrows(IllegalArgumentException.class, () -> _registry.addAll(arrayWithNull));
        assertThrows(IllegalArgumentException.class, () -> _registry.addAll(arrayWithNullId));
        assertThrows(IllegalArgumentException.class, () -> _registry.addAll(arrayWithEmptyId));
    }

    @Test
    void testClear() {
        HasIdAndNameStub item1 = new HasIdAndNameStub("id1", "name1");
        HasIdAndNameStub item2 = new HasIdAndNameStub("id2", "name2");
        HasIdAndNameStub item3 = new HasIdAndNameStub("id3", "name3");
        _registry.add(item1);
        _registry.add(item2);
        _registry.add(item3);

        _registry.clear();

        assertEquals(0, _registry.size());
        assertFalse(_registry.contains(item1));
        assertFalse(_registry.contains(item2));
        assertFalse(_registry.contains(item3));
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
        assertFalse(_registry.remove(_hasIdAndStub));

        _registry.add(_hasIdAndStub);

        assertTrue(_registry.remove(_hasIdAndStub));
        assertFalse(_registry.remove(_hasIdAndStub));
    }

    @Test
    void testRemoveAndContainsWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _registry.remove(((HasIdAndNameStub)null)));
        assertThrows(IllegalArgumentException.class,
                () -> _registry.remove(new HasIdAndNameStub(null, "name")));
        assertThrows(IllegalArgumentException.class,
                () -> _registry.remove(new HasIdAndNameStub("", "name")));
        assertThrows(IllegalArgumentException.class, () -> _registry.remove((String) null));
        assertThrows(IllegalArgumentException.class, () -> _registry.remove(""));
        assertThrows(IllegalArgumentException.class,
                () -> _registry.contains((HasIdAndNameStub)null));
        assertThrows(IllegalArgumentException.class, () -> _registry.contains((String) null));
        assertThrows(IllegalArgumentException.class, () -> _registry.contains(""));
    }

    @Test
    void testRepresentation() {
        HasIdAndNameStub item1 = new HasIdAndNameStub("id1", "name1");
        HasIdAndNameStub item2 = new HasIdAndNameStub("id2", "name2");
        HasIdAndNameStub item3 = new HasIdAndNameStub("id3", "name3");
        _registry.add(item1);
        _registry.add(item2);
        _registry.add(item3);

        ReadableCollection<HasIdAndNameStub> representation = _registry.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.contains(item1));
        assertTrue(representation.contains(item2));
        assertTrue(representation.contains(item3));
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
