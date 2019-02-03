package inaugural.soliloquy.common.test.java;

import inaugural.soliloquy.common.CoordinateFactory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import soliloquy.common.specs.ICoordinate;

public class CoordinateFactoryTests extends TestCase {
	private CoordinateFactory _coordinateFactory;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
	public CoordinateFactoryTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CoordinateFactoryTests.class );
    }
    
    @Override
    protected void setUp() throws Exception
    {
    	_coordinateFactory = new CoordinateFactory();
    }
    
    public void testMake()
    {
    	ICoordinate coordinate = _coordinateFactory.make(0,0);
    	assertTrue(coordinate instanceof ICoordinate);
    	
    	coordinate.setX(10);
    	assertTrue(coordinate.getX() == 10);
    }
}
