package inaugural.soliloquy.common.test.java;

import inaugural.soliloquy.common.CollectionFactory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import soliloquy.common.specs.ICollection;

public class CollectionFactoryTests extends TestCase {
	private CollectionFactory _collectionFactory;

	private String STRING1 = "STRING1";
	private String STRING2 = "STRING2";
	private String STRING3 = "STRING3";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CollectionFactoryTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CollectionFactoryTests.class );
    }
    
    @Override
    protected void setUp() throws Exception
    {
    	_collectionFactory = new CollectionFactory();
    }
    
    public void testMake()
    {
    	ICollection<String> strings = _collectionFactory.make("Hello");
    	assertTrue(strings instanceof ICollection<?>);
    	
    	strings.add("Hello");
    	assertTrue(strings.contains("Hello"));
    	assertTrue(strings.getArchetype().equals("Hello"));
    }
    
    @SuppressWarnings("unused")
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
    
    public void testMakeFromArray()
    {
    	ICollection<String> newCollection = _collectionFactory.make(new String[] {STRING1, STRING2, STRING3}, "");
    	
    	assertTrue(newCollection.size() == 3);
    	assertTrue(newCollection.get(0) == STRING1);
    	assertTrue(newCollection.get(1) == STRING2);
    	assertTrue(newCollection.get(2) == STRING3);
    }
}
