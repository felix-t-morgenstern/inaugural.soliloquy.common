package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.CollectionFactory;
import soliloquy.common.specs.ICollection;

public class CollectionFactoryTests {
	private CollectionFactory _collectionFactory;

	private String STRING1 = "STRING1";
	private String STRING2 = "STRING2";
	private String STRING3 = "STRING3";

    @BeforeEach
    protected void setUp() throws Exception
    {
    	_collectionFactory = new CollectionFactory();
    }

    @Test
    public void testMake()
    {
    	ICollection<String> strings = _collectionFactory.make("Hello");
    	assertTrue(strings instanceof ICollection<?>);
    	
    	strings.add("Hello");
    	assertTrue(strings.contains("Hello"));
    	assertTrue(strings.getArchetype().equals("Hello"));
    }
    
    @SuppressWarnings("unused")
    @Test
	public void testMakeWithNullArchetype()
    {
    	try {
        	ICollection<String> strings = _collectionFactory.make(null);
        	assertTrue(false);
    	}
    	catch(IllegalArgumentException e)
    	{
    		assertTrue(true);
    	}
    	catch(Exception e)
    	{
    		assertTrue(false);
    	}
    }

    @Test
    public void testMakeFromArray()
    {
    	ICollection<String> newCollection = _collectionFactory.make(new String[] {STRING1, STRING2, STRING3}, "");
    	
    	assertTrue(newCollection.size() == 3);
    	assertTrue(newCollection.get(0) == STRING1);
    	assertTrue(newCollection.get(1) == STRING2);
    	assertTrue(newCollection.get(2) == STRING3);
    }
}
