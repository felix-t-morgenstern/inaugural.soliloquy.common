package inaugural.soliloquy.common.test.java;

import org.mockito.Mockito;

import inaugural.soliloquy.common.Coordinate;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import soliloquy.common.specs.ICoordinate;

public class CoordinateTests extends TestCase {
	private Coordinate coordinate;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CoordinateTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CoordinateTests.class );
    }
    
    @Override
    protected void setUp() throws Exception
    {
    	Mockito.reset();
    	coordinate = new Coordinate(0,0);
    }
    
    public void testGetAndSetXAndY()
    {
    	coordinate.setX(123);
    	assertTrue(coordinate.getX() == 123);
    	coordinate.setY(456);
    	assertTrue(coordinate.getY() == 456);
    }
    
    public void testCompareTo()
    {
    	coordinate.setX(0);
    	coordinate.setY(0);
    	
    	Coordinate otherCoordinate = new Coordinate(0,0);
    	
    	assertTrue(coordinate.compareTo(otherCoordinate) == 0);
    	
    	otherCoordinate.setY(1);
    	assertTrue(coordinate.compareTo(otherCoordinate) == -1);
    	
    	otherCoordinate.setY(2);
    	assertTrue(coordinate.compareTo(otherCoordinate) == -3);
    	
    	coordinate.setX(2);
    	assertTrue(coordinate.compareTo(otherCoordinate) == 2);
    	
    	coordinate.setX(3);
    	assertTrue(coordinate.compareTo(otherCoordinate) == 6);
    }
    
    public void testMakeClone()
    {
    	coordinate.setX(123);
    	coordinate.setY(456);
    	ICoordinate cloned = coordinate.makeClone();
    	coordinate.setX(-123);
    	coordinate.setY(-456);
    	
    	assertTrue(cloned.getX() == 123);
    	assertTrue(cloned.getY() == 456);
    }
}
