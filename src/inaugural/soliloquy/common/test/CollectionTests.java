package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inaugural.soliloquy.common.Collection;
import soliloquy.common.specs.ICollection;

public class CollectionTests {
	private ICollection<Integer> _collection;
	
    @BeforeEach
    protected void setUp() throws Exception {
    	_collection = new Collection<Integer>(123123);
    }

    @Test
    public void testAddAndContains() {
    	_collection.add(null);
        assertTrue(_collection.contains(null));
        _collection.add(123);
        assertTrue(_collection.contains(123));
    }

    @Test
    public void testIsEmpty() {
    	assertTrue(_collection.isEmpty());
    	_collection.add(0);
    	assertTrue(!_collection.isEmpty());
    }

    @Test
    public void testClear() {
    	_collection.add(0);
    	_collection.clear();
    	assertTrue(_collection.isEmpty());
    }

    @Test
    public void testSize() {
    	assertTrue(_collection.size() == 0);
    	_collection.add(0);
    	assertTrue(_collection.size() == 1);
    }

    @Test
    public void testContains() {
    	assertTrue(!_collection.contains(0));
    	_collection.add(0);
    	assertTrue(_collection.contains(0));
    }

    @Test
    public void testAddAllArray() {
    	Integer[] ints = {1, 2, 3};
    	_collection.addAll(ints);
    	assertTrue(_collection.contains(1));
    	assertTrue(_collection.contains(2));
    	assertTrue(_collection.contains(3));
    }

    @Test
    public void testAddAllCollection() {
    	Collection<Integer> toAdd = new Collection<Integer>(null);
    	toAdd.add(1);
    	toAdd.add(2);
    	toAdd.add(3);
    	_collection.addAll(toAdd);
    	assertTrue(_collection.contains(1));
    	assertTrue(_collection.contains(2));
    	assertTrue(_collection.contains(3));
    }

    @Test
    public void testGet() {
    	_collection.add(3);
    	assertTrue(_collection.get(0) == 3);
    }

    @Test
    public void testToArray() {
    	Integer[] ints = {1, 2, 3};
    	_collection.add(1);
    	_collection.add(2);
    	_collection.add(3);
    	Object[] toArray = _collection.toArray();
    	assertTrue(ints.length == toArray.length);
    	for(int i = 0; i < ints.length; i++) {
    		assertTrue(ints[i] == (Integer)toArray[i]);
    	}
    }

    @Test
    public void testIterator() {
    	_collection.add(1);
    	_collection.add(2);
    	_collection.add(3);
    	int i = 0;
    	for(Integer item : _collection) {
    		assertTrue(item == ++i);
    	}
    }

    @Test
    public void testEquals() {
    	Collection<Integer> newCollection = null;
    	assertTrue(!_collection.equals(newCollection));
    	newCollection = new Collection<Integer>(null);
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
    public void testMakeClone() {
    	_collection.add(1);
    	_collection.add(2);
    	ICollection<Integer> newCollection = _collection.makeClone();
    	assertTrue(_collection.equals(newCollection));
    }

    @Test
    public void testRemoveItem() {
    	assertTrue(!_collection.removeItem(1));
    	_collection.add(1);
    	assertTrue(_collection.contains(1));
    	assertTrue(_collection.removeItem(1));
    	assertTrue(!_collection.contains(1));
    }

    @Test
    public void testGetParameterizedClassName() {
    	ICollection<String> strings = new Collection<String>("");
    	assertTrue("soliloquy.common.specs.ICollection<java.lang.String>".equals(strings.getInterfaceName()));
    	
    	ICollection<ICollection<ICollection<String>>> stringsCeption = new Collection<ICollection<ICollection<String>>>(new Collection<ICollection<String>>(new Collection<String>("")));
    	assertTrue("soliloquy.common.specs.ICollection<soliloquy.common.specs.ICollection<soliloquy.common.specs.ICollection<java.lang.String>>>".equals(stringsCeption.getInterfaceName()));
    }

    @Test
    public void testGetArchetype() {
    	assertTrue(_collection.getArchetype() == 123123);
    }
}
