package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.test.stubs.CollectionValidatorStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.common.CollectionImpl;
import soliloquy.specs.common.entities.Function;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;

import static org.junit.jupiter.api.Assertions.*;

class CollectionImplTests {
    private Collection<Integer> _collection;

    @BeforeEach
    void setUp() {
        _collection = new CollectionImpl<>(123123);
    }

    @Test
    void testAddAndContains() {
        _collection.add(null);
        assertTrue(_collection.contains(null));
        _collection.add(123);
        assertTrue(_collection.contains(123));
    }

    @Test
    void testIsEmpty() {
        assertTrue(_collection.isEmpty());
        _collection.add(0);
        assertTrue(!_collection.isEmpty());
    }

    @Test
    void testClear() {
        _collection.add(0);
        _collection.clear();
        assertTrue(_collection.isEmpty());
    }

    @Test
    void testSize() {
        assertEquals(0, _collection.size());
        _collection.add(0);
        assertEquals(1, _collection.size());
    }

    @Test
    void testContains() {
        assertTrue(!_collection.contains(0));
        _collection.add(0);
        assertTrue(_collection.contains(0));
    }

    @Test
    void testAddAllArray() {
        Integer[] ints = {1, 2, 3};
        _collection.addAll(ints);
        assertTrue(_collection.contains(1));
        assertTrue(_collection.contains(2));
        assertTrue(_collection.contains(3));
    }

    @Test
    void testAddAllCollection() {
        CollectionImpl<Integer> toAdd = new CollectionImpl<>(null);
        toAdd.add(1);
        toAdd.add(2);
        toAdd.add(3);
        _collection.addAll(toAdd);
        assertTrue(_collection.contains(1));
        assertTrue(_collection.contains(2));
        assertTrue(_collection.contains(3));
    }

    @Test
    void testGet() {
        _collection.add(3);
        assertEquals(3, (int) _collection.get(0));
    }

    @Test
    void testToArray() {
        Integer[] ints = {1, 2, 3};
        _collection.add(1);
        _collection.add(2);
        _collection.add(3);
        Object[] toArray = _collection.toArray();
        assertEquals(ints.length, toArray.length);
        for(int i = 0; i < ints.length; i++) {
            assertSame(ints[i], toArray[i]);
        }
    }

    @Test
    void testIterator() {
        _collection.add(1);
        _collection.add(2);
        _collection.add(3);
        int i = 0;
        for(Integer item : _collection) {
            assertEquals((int) item, ++i);
        }
    }

    @Test
    void testEquals() {
        CollectionImpl<Integer> newCollection = null;
        assertTrue(!_collection.equals(newCollection));
        newCollection = new CollectionImpl<>(null);
        _collection.add(1);
        assertTrue(!_collection.equals(newCollection));
        newCollection.add(1);
        assertTrue(_collection.equals(newCollection));
        _collection.add(2);
        assertTrue(!_collection.equals(newCollection));
        newCollection.add(3);
        assertTrue(!_collection.equals(newCollection));
    }

    @Test
    void testMakeClone() {
        Function<Integer,String> validator = new CollectionValidatorStub<>();
        _collection.validators().add(validator);
        _collection.add(1);
        _collection.add(2);

        Collection<Integer> newCollection = _collection.makeClone();

        assertTrue(_collection.equals(newCollection));
        assertNotNull(newCollection.getArchetype());
        assertTrue(newCollection.validators().contains(validator));
    }

    @Test
    void testRemoveItem() {
        assertTrue(!_collection.removeItem(1));
        _collection.add(1);
        assertTrue(_collection.contains(1));
        assertTrue(_collection.removeItem(1));
        assertTrue(!_collection.contains(1));
    }

    @Test
    void testValidators() {
        assertThrows(IllegalArgumentException.class, () -> _collection.validators().add(null));
        _collection.validators().add(new CollectionValidatorStub<>());
        assertThrows(IllegalArgumentException.class,
                () -> _collection.add(CollectionValidatorStub.ILLEGAL_VALUE));
        assertThrows(IllegalArgumentException.class,
                () -> _collection.addAll(new Integer[]{1,2,3,123}));
    }

    @Test
    void testReadOnlyRepresentation() {
        _collection.add(1);
        _collection.add(2);
        _collection.add(3);
        ReadableCollection<Integer> ReadableCollection = _collection.readOnlyRepresentation();

        assertNotNull(ReadableCollection);
        assertFalse(ReadableCollection instanceof Collection);
        assertEquals(3, ReadableCollection.size());
        assertTrue(ReadableCollection.contains(1));
        assertTrue(ReadableCollection.contains(2));
        assertTrue(ReadableCollection.contains(3));
    }

    @Test
    void testGetParameterizedClassName() {
        Collection<String> strings = new CollectionImpl<>("");
        assertEquals(Collection.class.getCanonicalName() + "<" +
                        String.class.getCanonicalName() + ">",
                strings.getInterfaceName());

        Collection<Collection<Collection<String>>> stringsCeption =
                new CollectionImpl<>(new CollectionImpl<>(new CollectionImpl<>("")));
        assertEquals(Collection.class.getCanonicalName() + "<" +
                        Collection.class.getCanonicalName() + "<" +
                        Collection.class.getCanonicalName() + "<" +
                        String.class.getCanonicalName() + ">>>",
                stringsCeption.getInterfaceName());
    }

    @Test
    void testGetArchetype() {
        assertEquals(123123, (int) _collection.getArchetype());
    }
}
