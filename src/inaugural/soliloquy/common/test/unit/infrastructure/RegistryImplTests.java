package inaugural.soliloquy.common.test.unit.infrastructure;

import inaugural.soliloquy.common.infrastructure.RegistryImpl;
import inaugural.soliloquy.common.test.fakes.FakeHasIdAndName;
import inaugural.soliloquy.common.test.fakes.FakeList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistryImplTests {
    private static FakeHasIdAndName ARCHETYPE = new FakeHasIdAndName("", "");
    private static String ID = "id";
    private static String NAME = "name";

    private Registry<FakeHasIdAndName> _registry;
    private FakeHasIdAndName _hasIdAndStub = new FakeHasIdAndName(ID, NAME);

    @BeforeEach
    void setUp() {
        _registry = new RegistryImpl<>(ARCHETYPE);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _registry = new RegistryImpl<>(null));
    }

    @Test
    void testArchetype() {
        assertSame(ARCHETYPE, _registry.getArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Registry.class.getCanonicalName() + "<" +
                        FakeHasIdAndName.class.getCanonicalName() + ">",
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
                () -> _registry.add(new FakeHasIdAndName(null, NAME)));
        assertThrows(IllegalArgumentException.class,
                () -> _registry.add(new FakeHasIdAndName("", NAME)));
    }

    @Test
    void testAddAllFromCollection() {
        FakeHasIdAndName item1 = new FakeHasIdAndName("id1", "name1");
        FakeHasIdAndName item2 = new FakeHasIdAndName("id2", "name2");
        FakeHasIdAndName item3 = new FakeHasIdAndName("id3", "name3");
        ArrayList<FakeHasIdAndName> toAdd = new ArrayList<>() {{
            add(item1);
            add(item2);
            add(item3);
        }};

        _registry.addAll(toAdd);

        assertTrue(_registry.contains(item1));
        assertTrue(_registry.contains(item2));
        assertTrue(_registry.contains(item3));
    }

    @Test
    void testAddAllFromCollectionWithNull() {
        assertThrows(IllegalArgumentException.class,
                () -> _registry.addAll((Collection<FakeHasIdAndName>) null));
    }

    @Test
    void testAddAllFromTypedArrayWithNull() {
        assertThrows(IllegalArgumentException.class,
                () -> _registry.addAll((FakeHasIdAndName[]) null));
    }

    @Test
    void testAddAllFromUntypedArrayWithNull() {
        assertThrows(IllegalArgumentException.class, () -> _registry.addAll((Object[]) null));
    }

    @Test
    void testAddAllFromCollectionWithInvalidEntries() {
        Collection<FakeHasIdAndName> collectionWithNull = new FakeList<>();
        collectionWithNull.add(null);

        Collection<FakeHasIdAndName> collectionWithNullId = new FakeList<>();
        collectionWithNullId.add(new FakeHasIdAndName(null, "name"));

        Collection<FakeHasIdAndName> collectionWithEmptyId = new FakeList<>();
        collectionWithEmptyId.add(new FakeHasIdAndName("", "name"));

        assertThrows(IllegalArgumentException.class, () -> _registry.addAll(collectionWithNull));
        assertThrows(IllegalArgumentException.class, () -> _registry.addAll(collectionWithNullId));
        assertThrows(IllegalArgumentException.class,
                () -> _registry.addAll(collectionWithEmptyId));
    }

    @Test
    void testAddAllFromUntypedArray() {
        FakeHasIdAndName item1 = new FakeHasIdAndName("id1", "name1");
        FakeHasIdAndName item2 = new FakeHasIdAndName("id2", "name2");
        FakeHasIdAndName item3 = new FakeHasIdAndName("id3", "name3");
        Object[] array = new Object[3];
        array[0] = item1;
        array[1] = item2;
        array[2] = item3;

        _registry.addAll(array);

        assertTrue(_registry.contains(item1));
        assertTrue(_registry.contains(item2));
        assertTrue(_registry.contains(item3));
    }

    @Test
    void testAddAllFromTypedArray() {
        FakeHasIdAndName item1 = new FakeHasIdAndName("id1", "name1");
        FakeHasIdAndName item2 = new FakeHasIdAndName("id2", "name2");
        FakeHasIdAndName item3 = new FakeHasIdAndName("id3", "name3");
        FakeHasIdAndName[] array = new FakeHasIdAndName[3];
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
                () -> _registry.addAll((FakeHasIdAndName[]) null));
    }

    @Test
    void testAddAllFromTypedArrayWithInvalidEntries() {
        FakeHasIdAndName[] arrayWithNull = new FakeHasIdAndName[1];
        arrayWithNull[0] = null;

        FakeHasIdAndName[] arrayWithNullId = new FakeHasIdAndName[1];
        arrayWithNullId[0] = new FakeHasIdAndName(null, "name");

        FakeHasIdAndName[] arrayWithEmptyId = new FakeHasIdAndName[1];
        arrayWithEmptyId[0] = new FakeHasIdAndName("", "name");

        assertThrows(IllegalArgumentException.class, () -> _registry.addAll(arrayWithNull));
        assertThrows(IllegalArgumentException.class, () -> _registry.addAll(arrayWithNullId));
        assertThrows(IllegalArgumentException.class, () -> _registry.addAll(arrayWithEmptyId));
    }

    @Test
    void testAddAllFromUntypedArrayWithInvalidEntries() {
        Object[] untypedArray = new Object[1];
        untypedArray[0] = 123;

        assertThrows(IllegalArgumentException.class, () -> _registry.addAll(untypedArray));
    }

    @Test
    void testClear() {
        FakeHasIdAndName item1 = new FakeHasIdAndName("id1", "name1");
        FakeHasIdAndName item2 = new FakeHasIdAndName("id2", "name2");
        FakeHasIdAndName item3 = new FakeHasIdAndName("id3", "name3");
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
                () -> _registry.remove(((FakeHasIdAndName) null)));
        assertThrows(IllegalArgumentException.class,
                () -> _registry.remove(new FakeHasIdAndName(null, "name")));
        assertThrows(IllegalArgumentException.class,
                () -> _registry.remove(new FakeHasIdAndName("", "name")));
        assertThrows(IllegalArgumentException.class, () -> _registry.remove((String) null));
        assertThrows(IllegalArgumentException.class, () -> _registry.remove(""));
        assertThrows(IllegalArgumentException.class,
                () -> _registry.contains((FakeHasIdAndName) null));
        assertThrows(IllegalArgumentException.class, () -> _registry.contains((String) null));
        assertThrows(IllegalArgumentException.class, () -> _registry.contains(""));
    }

    @Test
    void testRepresentation() {
        FakeHasIdAndName item1 = new FakeHasIdAndName("id1", "name1");
        FakeHasIdAndName item2 = new FakeHasIdAndName("id2", "name2");
        FakeHasIdAndName item3 = new FakeHasIdAndName("id3", "name3");
        _registry.add(item1);
        _registry.add(item2);
        _registry.add(item3);

        List<FakeHasIdAndName> representation = _registry.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.contains(item1));
        assertTrue(representation.contains(item2));
        assertTrue(representation.contains(item3));
    }

    @Test
    void testSize() {
        _registry.add(new FakeHasIdAndName("id1", "name1"));
        _registry.add(new FakeHasIdAndName("id2", "name2"));
        _registry.add(new FakeHasIdAndName("id3", "name3"));

        assertEquals(3, _registry.size());
    }

    @Test
    void testIterator() {
        _registry.add(new FakeHasIdAndName("id1", "name1"));
        _registry.add(new FakeHasIdAndName("id2", "name2"));
        _registry.add(new FakeHasIdAndName("id3", "name3"));

        for (FakeHasIdAndName hasIdAndNameStub : _registry) {
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
