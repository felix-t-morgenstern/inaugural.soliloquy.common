package inaugural.soliloquy.common.test;

import inaugural.soliloquy.common.test.stubs.CollectionValidatorStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.common.Collection;
import soliloquy.specs.common.valueobjects.ICollection;

import static org.junit.jupiter.api.Assertions.*;

class CollectionTests {
	private ICollection<Integer> _collection;
	
    @BeforeEach
	void setUp() {
    	_collection = new Collection<>(123123);
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
    	Collection<Integer> toAdd = new Collection<>(null);
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
    	Collection<Integer> newCollection = null;
    	assertTrue(!_collection.equals(newCollection));
    	newCollection = new Collection<>(null);
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
    	_collection.add(1);
    	_collection.add(2);
    	ICollection<Integer> newCollection = _collection.makeClone();
    	assertTrue(_collection.equals(newCollection));
    	assertNotNull(newCollection.getArchetype());
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
	void testGetParameterizedClassName() {
    	ICollection<String> strings = new Collection<>("");
		assertEquals(ICollection.class.getCanonicalName() + "<" +
						String.class.getCanonicalName() + ">",
				strings.getInterfaceName());
    	
    	ICollection<ICollection<ICollection<String>>> stringsCeption =
				new Collection<>(new Collection<>(new Collection<>("")));
		assertEquals(ICollection.class.getCanonicalName() + "<" +
						ICollection.class.getCanonicalName() + "<" +
						ICollection.class.getCanonicalName() + "<" +
						String.class.getCanonicalName() + ">>>",
				stringsCeption.getInterfaceName());
    }

    @Test
	void testGetArchetype() {
		assertEquals(123123, (int) _collection.getArchetype());
    }
}
