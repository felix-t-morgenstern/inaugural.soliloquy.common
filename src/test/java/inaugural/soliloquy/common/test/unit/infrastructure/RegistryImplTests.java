package inaugural.soliloquy.common.test.unit.infrastructure;

import inaugural.soliloquy.common.infrastructure.RegistryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegistryImplTests {
    private final String ID = randomString();
    private final String ID_1 = randomString();
    private final String ID_2 = randomString();
    private final String ID_3 = randomString();
    private final String TYPE = randomString();

    private final HasId MOCK_HAS_ID = generateMockHasId(ID);
    private final HasId ITEM_1 = generateMockHasId(ID_1);
    private final HasId ITEM_2 = generateMockHasId(ID_2);
    private final HasId ITEM_3 = generateMockHasId(ID_3);

    private HasId archetype;

    private Registry<HasId> registry;

    @BeforeEach
    void setUp() {
        archetype = mock(HasId.class);
        when(archetype.getInterfaceName()).thenReturn(TYPE);

        registry = new RegistryImpl<>(archetype);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                registry = new RegistryImpl<>(null));
    }

    @Test
    void testArchetype() {
        assertSame(archetype, registry.getArchetype());
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Registry.class.getCanonicalName() + "<" + TYPE + ">",
                registry.getInterfaceName());
    }

    @Test
    void testAddAndGet() {
        registry.add(MOCK_HAS_ID);

        assertSame(MOCK_HAS_ID, registry.get(ID));
    }

    @Test
    void testContainsItem() {
        registry.add(MOCK_HAS_ID);

        assertTrue(registry.contains(MOCK_HAS_ID));
    }

    @Test
    void testContainsId() {
        assertFalse(registry.contains(ID));

        registry.add(MOCK_HAS_ID);

        assertTrue(registry.contains(ID));
    }

    @Test
    void testAddNull() {
        assertThrows(IllegalArgumentException.class, () -> registry.add(null));
    }

    @Test
    void testAddWithInvalidIds() {
        HasId nullId = generateMockHasId(null);
        HasId emptyId = generateMockHasId("");

        assertThrows(IllegalArgumentException.class, () -> registry.add(nullId));
        assertThrows(IllegalArgumentException.class, () -> registry.add(emptyId));
    }

    @Test
    void testAddAllFromCollection() {
        String id1 = randomString();
        String id2 = randomString();
        String id3 = randomString();
        HasId hasId1 = generateMockHasId(id1);
        HasId hasId2 = generateMockHasId(id2);
        HasId hasId3 = generateMockHasId(id3);
        ArrayList<HasId> toAdd = new ArrayList<>() {{
            add(hasId1);
            add(hasId2);
            add(hasId3);
        }};

        registry.addAll(toAdd);

        assertTrue(registry.contains(hasId1));
        assertTrue(registry.contains(hasId2));
        assertTrue(registry.contains(hasId3));
    }

    @Test
    void testAddAllFromCollectionWithNull() {
        assertThrows(IllegalArgumentException.class,
                () -> registry.addAll((Collection<HasId>) null));
    }

    @Test
    void testAddAllFromTypedArrayWithNull() {
        assertThrows(IllegalArgumentException.class,
                () -> registry.addAll((HasId[]) null));
    }

    @Test
    void testAddAllFromUntypedArrayWithNull() {
        assertThrows(IllegalArgumentException.class, () -> registry.addAll((Object[]) null));
    }

    @Test
    void testAddAllFromCollectionWithInvalidEntries() {
        Collection<HasId> collectionWithNull = new ArrayList<>() {{
            add(null);
        }};

        Collection<HasId> collectionWithNullId = new ArrayList<>() {{
            add(generateMockHasId(null));
        }};

        Collection<HasId> collectionWithEmptyId = new ArrayList<>() {{
            add(generateMockHasId(""));
        }};

        assertThrows(IllegalArgumentException.class, () -> registry.addAll(collectionWithNull));
        assertThrows(IllegalArgumentException.class, () -> registry.addAll(collectionWithNullId));
        assertThrows(IllegalArgumentException.class, () -> registry.addAll(collectionWithEmptyId));
    }

    @Test
    void testAddAllFromUntypedArray() {
        Object[] array = new Object[3];
        array[0] = ITEM_1;
        array[1] = ITEM_2;
        array[2] = ITEM_3;

        registry.addAll(array);

        assertTrue(registry.contains(ITEM_1));
        assertTrue(registry.contains(ITEM_2));
        assertTrue(registry.contains(ITEM_3));
    }

    @Test
    void testAddAllFromTypedArray() {
        HasId item1 = generateMockHasId("id1");
        HasId item2 = generateMockHasId("id2");
        HasId item3 = generateMockHasId("id3");
        HasId[] array = new HasId[3];
        array[0] = item1;
        array[1] = item2;
        array[2] = item3;

        registry.addAll(array);

        assertTrue(registry.contains(item1));
        assertTrue(registry.contains(item2));
        assertTrue(registry.contains(item3));
    }

    @Test
    void testAddAllFromArrayWithNull() {
        assertThrows(IllegalArgumentException.class, () -> registry.addAll((HasId[]) null));
    }

    @Test
    void testAddAllFromTypedArrayWithInvalidEntries() {
        HasId[] arrayWithNull = new HasId[1];
        arrayWithNull[0] = null;

        HasId[] arrayWithNullId = new HasId[1];
        arrayWithNullId[0] = generateMockHasId(null);

        HasId[] arrayWithEmptyId = new HasId[1];
        arrayWithEmptyId[0] = generateMockHasId("");

        assertThrows(IllegalArgumentException.class, () -> registry.addAll(arrayWithNull));
        assertThrows(IllegalArgumentException.class, () -> registry.addAll(arrayWithNullId));
        assertThrows(IllegalArgumentException.class, () -> registry.addAll(arrayWithEmptyId));
    }

    @Test
    void testAddAllFromUntypedArrayWithInvalidEntries() {
        Object[] untypedArray = new Object[1];
        untypedArray[0] = 123;

        assertThrows(IllegalArgumentException.class, () -> registry.addAll(untypedArray));
    }

    @Test
    void testClear() {
        registry.add(ITEM_1);
        registry.add(ITEM_2);
        registry.add(ITEM_3);

        registry.clear();

        assertEquals(0, registry.size());
        assertFalse(registry.contains(ITEM_1));
        assertFalse(registry.contains(ITEM_2));
        assertFalse(registry.contains(ITEM_3));
    }

    @Test
    void testRemoveById() {
        assertFalse(registry.contains(ID));

        registry.add(MOCK_HAS_ID);

        assertTrue(registry.remove(ID));
        assertFalse(registry.remove(ID));
        assertFalse(registry.contains(ID));
    }

    @Test
    void testRemoveByItem() {
        assertFalse(registry.remove(MOCK_HAS_ID));

        registry.add(MOCK_HAS_ID);

        assertTrue(registry.remove(MOCK_HAS_ID));
        assertFalse(registry.remove(MOCK_HAS_ID));
    }

    @Test
    void testRemoveAndContainsWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> registry.remove(((HasId) null)));
        assertThrows(IllegalArgumentException.class,
                () -> registry.remove(generateMockHasId(null)));
        assertThrows(IllegalArgumentException.class, () -> registry.remove(generateMockHasId("")));
        assertThrows(IllegalArgumentException.class, () -> registry.remove((String) null));
        assertThrows(IllegalArgumentException.class, () -> registry.remove(""));
        assertThrows(IllegalArgumentException.class, () -> registry.contains((HasId) null));
        assertThrows(IllegalArgumentException.class, () -> registry.contains((String) null));
        assertThrows(IllegalArgumentException.class, () -> registry.contains(""));
    }

    @Test
    void testRepresentation() {
        registry.add(ITEM_1);
        registry.add(ITEM_2);
        registry.add(ITEM_3);

        List<HasId> representation = registry.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.contains(ITEM_1));
        assertTrue(representation.contains(ITEM_2));
        assertTrue(representation.contains(ITEM_3));
    }

    @Test
    void testSize() {
        registry.add(ITEM_1);
        registry.add(ITEM_2);
        registry.add(ITEM_3);

        assertEquals(3, registry.size());
    }

    @Test
    void testIterator() {
        registry.add(ITEM_1);
        registry.add(ITEM_2);
        registry.add(ITEM_3);

        // NB: I'm aware this is a very awkward test condition, it's an arbitrary condition to
        // test the iterator.
        for (HasId hasId : registry) {
            if (hasId.id().equals(ID_1)) {
                assertSame(ITEM_1, hasId);
            }
            if (hasId.id().equals(ID_2)) {
                assertSame(ITEM_2, hasId);
            }
            if (hasId.id().equals(ID_3)) {
                assertSame(ITEM_3, hasId);
            }
        }
    }

    private static HasId generateMockHasId(String id) {
        HasId mock = mock(HasId.class);
        when(mock.id()).thenReturn(id);
        return mock;
    }
}
