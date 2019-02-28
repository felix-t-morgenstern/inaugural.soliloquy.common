package inaugural.soliloquy.common.test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import inaugural.soliloquy.common.Map;
import inaugural.soliloquy.common.test.stubs.MapValidatorStub;
import inaugural.soliloquy.common.test.stubs.PairFactoryStub;
import soliloquy.common.specs.ICollection;
import soliloquy.common.specs.IFunction;
import soliloquy.common.specs.IMap;
import soliloquy.common.specs.IPair;
import soliloquy.common.specs.IPairFactory;

public class MapTests {
	private Map<String,String> map;
	private IPair<String,String> pairMock;
	private IPair<String,String> pairMock2;
	private final String PAIR_1_KEY = "Key1";
	private final String PAIR_1_VALUE = "Value1";
	private final String PAIR_2_KEY = "Key2";
	private final String PAIR_2_VALUE = "Value2";
	private final String FIRST_ARCHETYPE = "FIRST_ARCHETYPE";
	private final String SECOND_ARCHETYPE = "SECOND_ARCHETYPE";
	private IFunction<IPair<String,String>,String> validatorStub;
	private ICollection<IPair<String,String>> pairCollectionMock;
	private Iterator<IPair<String,String>> pairCollectionIteratorMock;
	
	private final IPairFactory PAIR_FACTORY = new PairFactoryStub();
	
    @SuppressWarnings("unchecked")
	@BeforeEach
    protected void setUp() throws Exception
    {
    	Mockito.reset();
    	map = new Map<String,String>(PAIR_FACTORY,FIRST_ARCHETYPE,SECOND_ARCHETYPE);
    	
		pairMock = mock(IPair.class);
    	when(pairMock.getItem1()).thenReturn(PAIR_1_KEY);
    	when(pairMock.getItem2()).thenReturn(PAIR_1_VALUE);
    	
		pairMock2 = mock(IPair.class);
    	when(pairMock2.getItem1()).thenReturn(PAIR_2_KEY);
    	when(pairMock2.getItem2()).thenReturn(PAIR_2_VALUE);
    	
    	validatorStub = new MapValidatorStub();
    	
    	pairCollectionIteratorMock = mock(Iterator.class);
    	when(pairCollectionIteratorMock.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
    	when(pairCollectionIteratorMock.next()).thenReturn(pairMock).thenReturn(pairMock2);
    	
    	pairCollectionMock = mock(ICollection.class);
    	when(pairCollectionMock.iterator()).thenReturn(pairCollectionIteratorMock);
    }

    @Test
    public void testPutNullOrBlankKey()
    {
    	try
    	{
    		map.put(null, "String");
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
    	try
    	{
    		map.put("", "String");
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
    public void testPutAndContainsKeyAndValue()
    {
    	map.put("Key1", "Value1");
    	assertTrue(map.containsKey("Key1"));
    	assertTrue(map.containsValue("Value1"));
    }

    @Test
    public void testGet()
    {
    	map.put("Key1", "Value1");
    	assertTrue(map.get("Key1") == "Value1");
    }

    @Test
    public void testGetExceptions()
    {
    	try
    	{
    		map.get("");
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
    	
    	try
    	{
    		map.get(null);
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
	public void testPutAll()
    {
    	map.putAll(pairCollectionMock);
    	assertTrue(map.containsKey(PAIR_1_KEY));
    	assertTrue(map.containsKey(PAIR_2_KEY));
    }

    @Test
    public void testClear()
    {
    	map.put("String1", "String2");
    	map.clear();
    	assertTrue(map.isEmpty());
    }

    @Test
	public void testContains()
    {
    	assertTrue(!map.contains(pairMock));
    	map.put(PAIR_1_KEY, PAIR_1_VALUE);
    	assertTrue(map.contains(pairMock));
    }

    @Test
	public void testSize()
	{
		assertTrue(map.size() == 0);
		map.put(PAIR_1_KEY, PAIR_1_VALUE);
		assertTrue(map.size() == 1);
		map.clear();
		assertTrue(map.size() == 0);
		map.put("String 1", "String 1");
		map.put("String 2", "String 2");
		map.put("String 1", "String 2");
		assertTrue(map.size() == 2);
	}
	
	@SuppressWarnings("unchecked")
    @Test
	public void testEqualsCollection()
	{
		Iterator<String> stringsIteratorMock = mock(Iterator.class);
		when(stringsIteratorMock.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(stringsIteratorMock.next()).thenReturn("String1").thenReturn("String2").thenReturn("String3");
		
		ICollection<String> stringsMock = mock(ICollection.class);
		when(stringsMock.iterator()).thenReturn(stringsIteratorMock);
		when(stringsMock.size()).thenReturn(3);

		map.put("String1", "String1");
		assertTrue(!map.equals(stringsMock));
		map.put("String2", "String2");
		assertTrue(!map.equals(stringsMock));
		map.put("String3", "String3");
		assertTrue(map.equals(stringsMock));
		
		map.clear();

		Iterator<String> stringsIteratorMock_2 = mock(Iterator.class);
		when(stringsIteratorMock_2.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		when(stringsIteratorMock_2.next()).thenReturn("String1").thenReturn("String2").thenReturn("String3");
		
		ICollection<String> stringsMock_2 = mock(ICollection.class);
		when(stringsMock_2.iterator()).thenReturn(stringsIteratorMock_2);
		when(stringsMock_2.size()).thenReturn(3);
		
		map.put("String1", "String1");
		assertTrue(!map.equals(stringsMock_2));
		map.put("String2", "String2");
		assertTrue(!map.equals(stringsMock_2));
		map.put("String3", "String4");
		assertTrue(!map.equals(stringsMock_2));
	}

    @Test
	public void testEqualsException()
	{
		try
		{
			map.equals((IMap<String,String>)null);
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
		try
		{
			map.equals((ICollection<String>)null);
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
	public void testGetKeys()
	{
		map.put("Key1", "Value1");
		map.put("Key2", "Value2");
		map.put("Key3", "Value3");
		ICollection<String> ids = map.getKeys();
		
		String[] expectedIds = new String[] {"Key1", "Key2", "Key3"};
		Boolean[] expectedIdsFound = new Boolean[expectedIds.length];
		for(String id : ids)
		{
			for(Integer i = 0; i < expectedIds.length; i++)
			{
				if (id == expectedIds[i]) expectedIdsFound[i] = true;
				continue;
			}
		}
		for(Integer i = 0; i < expectedIdsFound.length; i++) assertTrue(expectedIdsFound[i]);
	}

    @Test
	public void testGetValues()
	{
		map.put("Key1", "Value1");
		map.put("Key2", "Value2");
		map.put("Key3", "Value3");
		ICollection<String> values = map.getValues();
		
		String[] expectedValues = new String[] {"Value1", "Value2", "Value3"};
		Boolean[] expectedValuesFound = new Boolean[expectedValues.length];
		for(String value : values)
		{
			for(Integer i = 0; i < expectedValues.length; i++)
			{
				if (value == expectedValues[i]) expectedValuesFound[i] = true;
				continue;
			}
		}
		for(Integer i = 0; i < expectedValuesFound.length; i++) assertTrue(expectedValuesFound[i]);
	}

    @Test
	public void testValidator()
	{
		map.setValidator(validatorStub);
		
		try
		{
			map.put(PAIR_2_KEY, PAIR_2_VALUE);
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
		
		try
		{
			map.putAll(pairCollectionMock);
			assertTrue(false);
		}
		catch(IllegalArgumentException e)
		{
			assertTrue(e.toString().contains("Input key (Key2) not equal to Key1"));
		}
		catch(Exception e)
		{
			assertTrue(false);
		}
	}

    @Test
	public void testEqualsMap()
	{
		Map<String,String> secondMap = new Map<String,String>(null,"","");
		map.put("Key1", "Value1");
		assertTrue(!map.equals(secondMap));
		secondMap.put("Key2", "Value2");
		assertTrue(!map.equals(secondMap));
		map.put("Key2", "Value2");
		secondMap.put("Key1", "Value1");
		assertTrue(map.equals(secondMap));
	}

    @Test
	public void testRemoveByKey()
	{
		map.put(PAIR_1_KEY, PAIR_1_VALUE);
		map.put(PAIR_2_KEY, PAIR_2_VALUE);
		assertTrue(map.size() == 2);
		
		assertTrue(map.removeByKey("not-a-key") == null);
		assertTrue(map.size() == 2);
		assertTrue(map.removeByKey(PAIR_1_KEY) == PAIR_1_VALUE);
		assertTrue(map.size() == 1);
	}

    @Test
	public void testRemoveByKeyAndValue()
	{
		map.put(PAIR_1_KEY, PAIR_1_VALUE);
		map.put(PAIR_2_KEY, PAIR_2_VALUE);
		assertTrue(map.size() == 2);
		
		assertTrue(!map.removeByKeyAndValue("not-a-key", "not-a-value"));
		assertTrue(map.size() == 2);
		assertTrue(map.removeByKeyAndValue(PAIR_1_KEY, PAIR_1_VALUE));
		assertTrue(map.size() == 1);
	}

    @Test
	public void testItemExists()
	{
		assertTrue(!map.itemExists(PAIR_1_KEY));
		
		map.put(PAIR_1_KEY, PAIR_1_VALUE);
		assertTrue(map.itemExists(PAIR_1_KEY));
	}

    @Test
	public void testIndicesOf()
	{
		assertTrue(map.indicesOf(PAIR_1_VALUE).size() == 0);
		
		map.put(PAIR_1_KEY, PAIR_1_VALUE);
		
		assertTrue(map.indicesOf(PAIR_1_VALUE).size() == 1);
		assertTrue(map.indicesOf(PAIR_1_VALUE).contains(PAIR_1_KEY));
	}

    @Test
	public void testIterator()
	{
		map.put(PAIR_1_KEY, PAIR_1_VALUE);
		map.put(PAIR_2_KEY, PAIR_2_VALUE);
		int counter = 0;
		for (IPair<String,String> pair : map)
		{
			if (counter == 0)
			{
				assertTrue(pair.getItem1().equals(PAIR_2_KEY));
				assertTrue(pair.getItem2().equals(PAIR_2_VALUE));
			}
			if (counter == 1)
			{
				assertTrue(pair.getItem1().equals(PAIR_1_KEY));
				assertTrue(pair.getItem2().equals(PAIR_1_VALUE));
			}
			counter++;
		}
	}

    @Test
	public void testGetFirstArchetype()
	{
		assertTrue(FIRST_ARCHETYPE.equals(map.getFirstArchetype()));
	}

    @Test
	public void testGetSecondArchetype()
	{
		assertTrue(SECOND_ARCHETYPE.equals(map.getSecondArchetype()));
	}

    @Test
	public void testGetInterfaceName()
	{
		assertTrue("soliloquy.common.specs.IMap<java.lang.String,java.lang.String>".equals(map.getInterfaceName()));
	}

    @Test
	public void testMakeClone()
	{
		map.put(PAIR_1_KEY, PAIR_1_VALUE);
		map.put(PAIR_2_KEY, PAIR_2_VALUE);
		
		IMap<String,String> clonedMap = map.makeClone();
		assertTrue(clonedMap != null);
		assertTrue(clonedMap.getFirstArchetype() == map.getFirstArchetype());
		assertTrue(clonedMap.getSecondArchetype() == map.getSecondArchetype());
		for(IPair<String,String> item : map)
		{
			assertTrue(clonedMap.contains(item));
		}
		assertTrue(map.size() == clonedMap.size());
	}
}
