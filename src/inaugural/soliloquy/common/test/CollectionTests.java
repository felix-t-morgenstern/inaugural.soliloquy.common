package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.Collection;
import soliloquy.common.specs.ICollection;

public class CollectionTests {
	private ICollection<Integer> collection;
	
    @BeforeEach
    protected void setUp() throws Exception
    {
    	collection = new Collection<Integer>(123123);
    }

    @Test
    public void testAddAndContains()
    {
    	collection.add(null);
        assertTrue(collection.contains(null));
        collection.add(123);
        assertTrue(collection.contains(123));
    }

    @Test
    public void testIsEmpty()
    {
    	assertTrue(collection.isEmpty());
    	collection.add(0);
    	assertTrue(!collection.isEmpty());
    }

    @Test
    public void testClear()
    {
    	collection.add(0);
    	collection.clear();
    	assertTrue(collection.isEmpty());
    }

    @Test
    public void testSize()
    {
    	assertTrue(collection.size() == 0);
    	collection.add(0);
    	assertTrue(collection.size() == 1);
    }

    @Test
    public void testContains()
    {
    	assertTrue(!collection.contains(0));
    	collection.add(0);
    	assertTrue(collection.contains(0));
    }

    @Test
    public void testAddAllArray()
    {
    	Integer[] ints = {1, 2, 3};
    	collection.addAll(ints);
    	assertTrue(collection.contains(1));
    	assertTrue(collection.contains(2));
    	assertTrue(collection.contains(3));
    }

    @Test
    public void testAddAllCollection()
    {
    	Collection<Integer> toAdd = new Collection<Integer>(null);
    	toAdd.add(1);
    	toAdd.add(2);
    	toAdd.add(3);
    	collection.addAll(toAdd);
    	assertTrue(collection.contains(1));
    	assertTrue(collection.contains(2));
    	assertTrue(collection.contains(3));
    }

    @Test
    public void testGet()
    {
    	collection.add(3);
    	assertTrue(collection.get(0) == 3);
    }

    @Test
    public void testToArray()
    {
    	Integer[] ints = {1, 2, 3};
    	collection.add(1);
    	collection.add(2);
    	collection.add(3);
    	Object[] toArray = collection.toArray();
    	assertTrue(ints.length == toArray.length);
    	for(int i = 0; i < ints.length; i++) assertTrue(ints[i] == (Integer)toArray[i]);
    }

    @Test
    public void testIterator()
    {
    	collection.add(1);
    	collection.add(2);
    	collection.add(3);
    	int i = 0;
    	for(Integer item : collection) assertTrue(item == ++i);
    }

    @Test
    public void testEquals()
    {
    	Collection<Integer> newCollection = null;
    	assertTrue(!collection.equals(newCollection));
    	newCollection = new Collection<Integer>(null);
    	collection.add(1);
    	assertTrue(!collection.equals(newCollection));
    	newCollection.add(1);
    	assertTrue(collection.equals(newCollection));
    	collection.add(2);
    	assertTrue(!collection.equals(newCollection));
    	newCollection.add(3);
    	assertTrue(!collection.equals(newCollection));
    }

    @Test
    public void testMakeClone()
    {
    	collection.add(1);
    	collection.add(2);
    	ICollection<Integer> newCollection = collection.makeClone();
    	assertTrue(collection.equals(newCollection));
    }

    @Test
    public void testRemoveItem()
    {
    	assertTrue(!collection.removeItem(1));
    	collection.add(1);
    	assertTrue(collection.contains(1));
    	assertTrue(collection.removeItem(1));
    	assertTrue(!collection.contains(1));
    }

    @Test
    public void testGetParameterizedClassName()
    {
    	ICollection<String> strings = new Collection<String>("");
    	assertTrue("soliloquy.common.specs.ICollection<java.lang.String>".equals(strings.getInterfaceName()));
    	
    	ICollection<ICollection<ICollection<String>>> stringsCeption = new Collection<ICollection<ICollection<String>>>(new Collection<ICollection<String>>(new Collection<String>("")));
    	assertTrue("soliloquy.common.specs.ICollection<soliloquy.common.specs.ICollection<soliloquy.common.specs.ICollection<java.lang.String>>>".equals(stringsCeption.getInterfaceName()));
    }

    @Test
    public void testGetArchetype()
    {
    	assertTrue(collection.getArchetype() == 123123);
    }
}
