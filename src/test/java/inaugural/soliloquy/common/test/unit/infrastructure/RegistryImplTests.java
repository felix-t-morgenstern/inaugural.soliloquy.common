package inaugural.soliloquy.common.test.unit.infrastructure;

import inaugural.soliloquy.common.infrastructure.RegistryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import soliloquy.specs.common.infrastructure.Registry;
import soliloquy.specs.common.shared.HasId;

import java.util.Collection;

import static inaugural.soliloquy.tools.collections.Collections.listOf;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistryImplTests {
    private final String ID = randomString();
    private final String ID_1 = randomString();
    private final String ID_2 = randomString();
    private final String ID_3 = randomString();
    private final String TYPE = randomString();

    private final HasId MOCK_HAS_ID = generateMockHasId(ID);
    private final HasId ITEM_1 = generateMockHasId(ID_1);
    private final HasId ITEM_2 = generateMockHasId(ID_2);
    private final HasId ITEM_3 = generateMockHasId(ID_3);

    @Mock private HasId mockArchetype;

    private Registry<HasId> registry;

    @Before
    public void setUp() {
        when(mockArchetype.getInterfaceName()).thenReturn(TYPE);

        registry = new RegistryImpl<>(mockArchetype);
    }

    @Test
    public void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                registry = new RegistryImpl<>(null));
    }

    @Test
    public void testArchetype() {
        assertSame(mockArchetype, registry.archetype());
    }

    @Test
    public void testGetInterfaceName() {
        assertEquals(Registry.class.getCanonicalName() + "<" + TYPE + ">",
                registry.getInterfaceName());
    }

    @Test
    public void testAddAndGet() {
        registry.add(MOCK_HAS_ID);

        assertSame(MOCK_HAS_ID, registry.get(ID));
    }

    @Test
    public void testContainsItem() {
        registry.add(MOCK_HAS_ID);

        assertTrue(registry.contains(MOCK_HAS_ID));
    }

    @Test
    public void testContainsId() {
        assertFalse(registry.contains(ID));

        registry.add(MOCK_HAS_ID);

        assertTrue(registry.contains(ID));
    }

    @Test
    public void testAddNull() {
        assertThrows(IllegalArgumentException.class, () -> registry.add(null));
    }

    @Test
    public void testAddWithInvalidIds() {
        var nullId = generateMockHasId(null);
        var emptyId = generateMockHasId("");

        assertThrows(IllegalArgumentException.class, () -> registry.add(nullId));
        assertThrows(IllegalArgumentException.class, () -> registry.add(emptyId));
    }

    @Test
    public void testAddAllFromCollection() {
        var id1 = randomString();
        var id2 = randomString();
        var id3 = randomString();
        var hasId1 = generateMockHasId(id1);
        var hasId2 = generateMockHasId(id2);
        var hasId3 = generateMockHasId(id3);
        var toAdd = listOf(hasId1, hasId2, hasId3);

        registry.addAll(toAdd);

        assertTrue(registry.contains(hasId1));
        assertTrue(registry.contains(hasId2));
        assertTrue(registry.contains(hasId3));
    }

    @Test
    public void testAddAllFromCollectionWithNull() {
        assertThrows(IllegalArgumentException.class,
                () -> registry.addAll((Collection<HasId>) null));
    }

    @Test
    public void testAddAllFromTypedArrayWithNull() {
        assertThrows(IllegalArgumentException.class,
                () -> registry.addAll((HasId[]) null));
    }

    @Test
    public void testAddAllFromUntypedArrayWithNull() {
        assertThrows(IllegalArgumentException.class, () -> registry.addAll((Object[]) null));
    }

    @Test
    public void testAddAllFromCollectionWithInvalidEntries() {
        Collection<HasId> collectionWithNull = listOf((HasId) null);
        Collection<HasId> collectionWithNullId = listOf(generateMockHasId(null));
        Collection<HasId> collectionWithEmptyId = listOf(generateMockHasId(""));

        assertThrows(IllegalArgumentException.class, () -> registry.addAll(collectionWithNull));
        assertThrows(IllegalArgumentException.class, () -> registry.addAll(collectionWithNullId));
        assertThrows(IllegalArgumentException.class, () -> registry.addAll(collectionWithEmptyId));
    }

    @Test
    public void testAddAllFromUntypedArray() {
        var array = new Object[3];
        array[0] = ITEM_1;
        array[1] = ITEM_2;
        array[2] = ITEM_3;

        registry.addAll(array);

        assertTrue(registry.contains(ITEM_1));
        assertTrue(registry.contains(ITEM_2));
        assertTrue(registry.contains(ITEM_3));
    }

    @Test
    public void testAddAllFromTypedArray() {
        var item1 = generateMockHasId("id1");
        var item2 = generateMockHasId("id2");
        var item3 = generateMockHasId("id3");
        var array = new HasId[3];
        array[0] = item1;
        array[1] = item2;
        array[2] = item3;

        registry.addAll(array);

        assertTrue(registry.contains(item1));
        assertTrue(registry.contains(item2));
        assertTrue(registry.contains(item3));
    }

    @Test
    public void testAddAllFromArrayWithNull() {
        assertThrows(IllegalArgumentException.class, () -> registry.addAll((HasId[]) null));
    }

    @Test
    public void testAddAllFromTypedArrayWithInvalidEntries() {
        var arrayWithNull = new HasId[1];
        arrayWithNull[0] = null;

        var arrayWithNullId = new HasId[1];
        arrayWithNullId[0] = generateMockHasId(null);

        var arrayWithEmptyId = new HasId[1];
        arrayWithEmptyId[0] = generateMockHasId("");

        assertThrows(IllegalArgumentException.class, () -> registry.addAll(arrayWithNull));
        assertThrows(IllegalArgumentException.class, () -> registry.addAll(arrayWithNullId));
        assertThrows(IllegalArgumentException.class, () -> registry.addAll(arrayWithEmptyId));
    }

    @Test
    public void testAddAllFromUntypedArrayWithInvalidEntries() {
        var untypedArray = new Object[1];
        untypedArray[0] = randomInt();

        assertThrows(IllegalArgumentException.class, () -> registry.addAll(untypedArray));
    }

    @Test
    public void testClear() {
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
    public void testRemoveById() {
        assertFalse(registry.contains(ID));

        registry.add(MOCK_HAS_ID);

        assertTrue(registry.remove(ID));
        assertFalse(registry.remove(ID));
        assertFalse(registry.contains(ID));
    }

    @Test
    public void testRemoveByItem() {
        assertFalse(registry.remove(MOCK_HAS_ID));

        registry.add(MOCK_HAS_ID);

        assertTrue(registry.remove(MOCK_HAS_ID));
        assertFalse(registry.remove(MOCK_HAS_ID));
    }

    @Test
    public void testRemoveAndContainsWithInvalidParams() {
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
    public void testRepresentation() {
        registry.add(ITEM_1);
        registry.add(ITEM_2);
        registry.add(ITEM_3);

        var representation = registry.representation();

        assertNotNull(representation);
        assertEquals(3, representation.size());
        assertTrue(representation.contains(ITEM_1));
        assertTrue(representation.contains(ITEM_2));
        assertTrue(representation.contains(ITEM_3));
    }

    @Test
    public void testSize() {
        registry.add(ITEM_1);
        registry.add(ITEM_2);
        registry.add(ITEM_3);

        assertEquals(3, registry.size());
    }

    @Test
    public void testIterator() {
        registry.add(ITEM_1);
        registry.add(ITEM_2);
        registry.add(ITEM_3);

        // NB: I'm aware this is a very awkward test condition, it's an arbitrary condition to
        // test the iterator.
        for (var hasId : registry) {
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
        var mock = mock(HasId.class);
        when(mock.id()).thenReturn(id);
        return mock;
    }
}
