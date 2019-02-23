package inaugural.soliloquy.common.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.mockito.Mockito.*;

import inaugural.soliloquy.common.GenericParamsSet;
import inaugural.soliloquy.common.GenericParamsSetFactory;
import inaugural.soliloquy.common.test.stubs.MapFactoryStub;
import soliloquy.common.specs.IGenericParamsSet;
import soliloquy.common.specs.IMapFactory;
import soliloquy.common.specs.IPersistentValuesHandler;

public class GenericParamsSetFactoryTests extends TestCase {
	private GenericParamsSetFactory _genericParamsSetFactory;
	
	private IPersistentValuesHandler _persistentValuesHandlerMock;
	
	private final IMapFactory MAP_FACTORY = new MapFactoryStub();
	
	private static final String PERSISTENT_VARIABLE_WRITE_VALUE = "GenericString";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GenericParamsSetFactoryTests( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( GenericParamsSetFactoryTests.class );
    }
    
    @Override
    protected void setUp() throws Exception
    {
    	_persistentValuesHandlerMock = mock(IPersistentValuesHandler.class);
    	when(_persistentValuesHandlerMock.writeValues(any())).thenReturn(PERSISTENT_VARIABLE_WRITE_VALUE);
    	when(_persistentValuesHandlerMock.makePersistentValueToWrite(any(),any())).thenReturn(null);

    	_genericParamsSetFactory = new GenericParamsSetFactory(_persistentValuesHandlerMock, MAP_FACTORY);
    	
    }
    
    public void testMake()
    {
    	IGenericParamsSet genericParamsSet = _genericParamsSetFactory.make();
    	assertTrue(genericParamsSet instanceof GenericParamsSet);
    	
    	genericParamsSet.addParam("Blah", 123);
    	assertTrue((Integer) genericParamsSet.getParam(Integer.class.getCanonicalName(), "Blah") == 123);
    	assertTrue(genericParamsSet.write().equals(PERSISTENT_VARIABLE_WRITE_VALUE));
    }
}
