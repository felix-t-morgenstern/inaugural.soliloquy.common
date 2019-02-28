package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.Collection;
import inaugural.soliloquy.common.PersistentValuesHandler;
import inaugural.soliloquy.common.test.stubs.PersistentValuesHandlerTestsReadAction;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IPersistentValueToRead;
import soliloquy.common.specs.IPersistentValueToWrite;
import soliloquy.common.specs.IPersistentValueTypeHandler;

public class PersistentValuesHandlerTests {
	private PersistentValuesHandler _persistentValuesHandler;

	private IPersistentValueTypeHandler<Integer> _persistentIntegerHandler;
	private IPersistentValueTypeHandler<String> _persistentStringHandler;

	private final Integer INT_PARAM_1_INT_VALUE = 123;
	private final String INT_PARAM_1_STR_VALUE = "123";
	private final Integer INT_PARAM_2_INT_VALUE = 456;
	private final String INT_PARAM_2_STR_VALUE = "456";
	private final Integer INT_PARAM_3_INT_VALUE = 789;
	private final String INT_PARAM_3_STR_VALUE = "789";

	private final String STR_PARAM_1_VALUE = "String1";
	private final String STR_PARAM_2_VALUE = "String2";
	private final String STR_PARAM_3_VALUE = "String3";
    
    @SuppressWarnings("unchecked")
    @BeforeEach
    protected void setUp() throws Exception
    {
    	_persistentValuesHandler = new PersistentValuesHandler();
    	
    	_persistentIntegerHandler = (IPersistentValueTypeHandler<Integer>) mock(IPersistentValueTypeHandler.class);
    	when(_persistentIntegerHandler.getArchetype()).thenReturn(0);
    	when(_persistentIntegerHandler.write(INT_PARAM_1_INT_VALUE)).thenReturn(INT_PARAM_1_STR_VALUE);
    	when(_persistentIntegerHandler.read(INT_PARAM_1_STR_VALUE)).thenReturn(INT_PARAM_1_INT_VALUE);
    	when(_persistentIntegerHandler.write(INT_PARAM_2_INT_VALUE)).thenReturn(INT_PARAM_2_STR_VALUE);
    	when(_persistentIntegerHandler.read(INT_PARAM_2_STR_VALUE)).thenReturn(INT_PARAM_2_INT_VALUE);
    	when(_persistentIntegerHandler.write(INT_PARAM_3_INT_VALUE)).thenReturn(INT_PARAM_3_STR_VALUE);
    	when(_persistentIntegerHandler.read(INT_PARAM_3_STR_VALUE)).thenReturn(INT_PARAM_3_INT_VALUE);
    	
    	_persistentStringHandler = (IPersistentValueTypeHandler<String>) mock(IPersistentValueTypeHandler.class);
    	when(_persistentStringHandler.getArchetype()).thenReturn("");
    	when(_persistentStringHandler.write(STR_PARAM_1_VALUE)).thenReturn(STR_PARAM_1_VALUE);
    	when(_persistentStringHandler.read(STR_PARAM_1_VALUE)).thenReturn(STR_PARAM_1_VALUE);
    	when(_persistentStringHandler.write(STR_PARAM_2_VALUE)).thenReturn(STR_PARAM_2_VALUE);
    	when(_persistentStringHandler.read(STR_PARAM_2_VALUE)).thenReturn(STR_PARAM_2_VALUE);
    	when(_persistentStringHandler.write(STR_PARAM_3_VALUE)).thenReturn(STR_PARAM_3_VALUE);
    	when(_persistentStringHandler.read(STR_PARAM_3_VALUE)).thenReturn(STR_PARAM_3_VALUE);
    }
    
    @Test
    public void testMakePersistentValueToRead()
    {
    	IPersistentValueToRead persistentValueToRead = _persistentValuesHandler.makePersistentValueToRead(Integer.class.getCanonicalName(), "Int_Param_1", "123");
    	assertTrue(persistentValueToRead.typeName() == Integer.class.getCanonicalName());
    	assertTrue(persistentValueToRead.name() == "Int_Param_1");
    	assertTrue(persistentValueToRead.value() == "123");
    }

    @Test
    public void testMakePersistentValueToWrite()
    {
    	IPersistentValueToWrite<Integer> persistentValueToWrite = _persistentValuesHandler.makePersistentValueToWrite("Int_Param_1", 123);
    	assertTrue(persistentValueToWrite.value().getClass() == Integer.class);
    	assertTrue(persistentValueToWrite.typeName() == Integer.class.getCanonicalName());
    	assertTrue(persistentValueToWrite.name() == "Int_Param_1");
    	assertTrue(persistentValueToWrite.value() == 123);
    }

    @Test
    public void testAddAndGetPersistentValueTypeHandler()
    {
    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
    	assertTrue(_persistentValuesHandler.<Integer>getPersistentValueTypeHandler(Integer.class.getCanonicalName()) == _persistentIntegerHandler);
    }

    @Test
    public void testAddPersistentValueTypeHandlerTwiceException()
    {
    	try
    	{
	    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
	    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
    		assertTrue(false);
    	}
    	catch (IllegalArgumentException e)
    	{
    		assertTrue(true);
    	}
    	catch (Exception e)
    	{
    		assertTrue(false);
    	}
    }

    @Test
    public void testRemovePersistentValueTypeHandler()
    {
    	assertTrue(!_persistentValuesHandler.removePersistentValueTypeHandler(Integer.class.getCanonicalName()));
    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
    	assertTrue(_persistentValuesHandler.<Integer>getPersistentValueTypeHandler(Integer.class.getCanonicalName()) == _persistentIntegerHandler);
    	assertTrue(_persistentValuesHandler.removePersistentValueTypeHandler(Integer.class.getCanonicalName()));
    }

    @Test
    public void testPersistentValueTypesHandled()
    {
    	assertTrue(_persistentValuesHandler.persistentValueTypesHandled().isEmpty());
    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentStringHandler);
    	assertTrue(_persistentValuesHandler.persistentValueTypesHandled().size() == 2);
    	assertTrue(_persistentValuesHandler.persistentValueTypesHandled().contains(Integer.class.getCanonicalName()));
    	assertTrue(_persistentValuesHandler.persistentValueTypesHandled().contains(String.class.getCanonicalName()));
    }

    @Test
    public void testReadValues()
    {
    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentStringHandler);
    	
    	String persistentValuesJson = "[]";
    	_persistentValuesHandler.readValues(persistentValuesJson, new PersistentValuesHandlerTestsReadAction(), true);
    	
    	assertTrue(PersistentValuesHandlerTestsReadAction._results.isEmpty());
    	PersistentValuesHandlerTestsReadAction._results.clear();
    	
    	persistentValuesJson = "[{\"typeName\": \"java.lang.Integer\", \"name\": \"Integer1\", \"value\": \"123\"},{\"typeName\": \"java.lang.Integer\", \"name\": \"Integer2\", \"value\": \"456\"},{\"typeName\": \"java.lang.String\", \"name\": \"String1\", \"value\": \"String1\"},{\"typeName\": \"java.lang.String\", \"name\": \"String2\", \"value\": \"String2\"}]";
    	_persistentValuesHandler.readValues(persistentValuesJson, new PersistentValuesHandlerTestsReadAction(), true);
    	assertTrue(PersistentValuesHandlerTestsReadAction._results.size() == 4);
    	for (IPersistentValueToWrite<?> persistentValueToWrite : PersistentValuesHandlerTestsReadAction._results)
    	{
    		if (persistentValueToWrite.name().equals("Integer1")) assertTrue((Integer) persistentValueToWrite.value() == INT_PARAM_1_INT_VALUE);
    		if (persistentValueToWrite.name().equals("Integer2")) assertTrue((Integer) persistentValueToWrite.value() == INT_PARAM_2_INT_VALUE);
    		if (persistentValueToWrite.name().equals("String1")) assertTrue(persistentValueToWrite.value().equals(STR_PARAM_1_VALUE));
    		if (persistentValueToWrite.name().equals("String2")) assertTrue(persistentValueToWrite.value().equals(STR_PARAM_2_VALUE));
    	}
    }

    @Test
    public void testWriteValues()
    {
    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentStringHandler);
    	
    	ICollection<IPersistentValueToWrite<?>> persistentValuesToProcess = new Collection<IPersistentValueToWrite<?>>(null);
    	persistentValuesToProcess.add(_persistentValuesHandler.makePersistentValueToWrite("String1",STR_PARAM_1_VALUE));
    	persistentValuesToProcess.add(_persistentValuesHandler.makePersistentValueToWrite("String2",STR_PARAM_2_VALUE));
    	persistentValuesToProcess.add(_persistentValuesHandler.makePersistentValueToWrite("Integer1",INT_PARAM_1_INT_VALUE));
    	persistentValuesToProcess.add(_persistentValuesHandler.makePersistentValueToWrite("Integer2",INT_PARAM_2_INT_VALUE));
    	String result = _persistentValuesHandler.writeValues(persistentValuesToProcess);
    	assertTrue(result.equals("[{\"typeName\":\"java.lang.String\",\"name\":\"String1\",\"value\":\"String1\"},{\"typeName\":\"java.lang.String\",\"name\":\"String2\",\"value\":\"String2\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"Integer1\",\"value\":\"123\"},{\"typeName\":\"java.lang.Integer\",\"name\":\"Integer2\",\"value\":\"456\"}]"));
    }
}
