package inaugural.soliloquy.common.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import inaugural.soliloquy.common.test.stubs.PersistentCollectionHandlerStub;
import inaugural.soliloquy.common.test.stubs.PersistentMapHandlerStub;
import inaugural.soliloquy.common.test.stubs.PersistentPairHandlerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inaugural.soliloquy.common.PersistentValuesHandler;
import soliloquy.specs.common.infrastructure.*;

class PersistentValuesHandlerTests {
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
	void setUp() {
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
	void testAddAndGetPersistentValueTypeHandler() {
    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
		assertSame(_persistentValuesHandler.<Integer>getPersistentValueTypeHandler(Integer.class.getCanonicalName()), _persistentIntegerHandler);
    }

    @Test
	void testAddPersistentValueTypeHandlerTwiceException() {
    	assertThrows(IllegalArgumentException.class, () -> {
			_persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
			_persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
		});
    }

    @Test
	void testRemovePersistentValueTypeHandler() {
    	assertTrue(!_persistentValuesHandler.removePersistentValueTypeHandler(Integer.class.getCanonicalName()));
    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
		assertSame(_persistentValuesHandler.<Integer>getPersistentValueTypeHandler(Integer.class.getCanonicalName()), _persistentIntegerHandler);
    	assertTrue(_persistentValuesHandler.removePersistentValueTypeHandler(Integer.class.getCanonicalName()));
    }

    @Test
	void testPersistentValueTypesHandled() {
    	assertTrue(_persistentValuesHandler.persistentValueTypesHandled().isEmpty());
    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
    	_persistentValuesHandler.addPersistentValueTypeHandler(_persistentStringHandler);
		assertEquals(2, _persistentValuesHandler.persistentValueTypesHandled().size());
    	assertTrue(_persistentValuesHandler.persistentValueTypesHandled().contains(Integer.class.getCanonicalName()));
    	assertTrue(_persistentValuesHandler.persistentValueTypesHandled().contains(String.class.getCanonicalName()));
    }

	@Test
	void testGenerateArchetype() {
		_persistentValuesHandler.addPersistentValueTypeHandler(_persistentIntegerHandler);
		assertNotNull(_persistentValuesHandler.generateArchetype(Integer.class.getCanonicalName()));
	}

	@Test
	void testRegisterPersistentCollectionHandler() {
		IPersistentCollectionHandler persistentCollectionHandler =
				new PersistentCollectionHandlerStub();
		_persistentValuesHandler.registerPersistentCollectionHandler(persistentCollectionHandler);

		assertSame(persistentCollectionHandler,
				_persistentValuesHandler.getPersistentValueTypeHandler(
						ICollection.class.getCanonicalName()));
	}

	@Test
	void testRegisterPersistentMapHandler() {
		IPersistentMapHandler persistentMapHandler = new PersistentMapHandlerStub();
		_persistentValuesHandler.registerPersistentMapHandler(persistentMapHandler);

		assertSame(persistentMapHandler,
				_persistentValuesHandler.getPersistentValueTypeHandler(
						IMap.class.getCanonicalName()));
	}

	@Test
	void testRegisterPersistentPairHandler() {
		IPersistentPairHandler persistentPairHandler = new PersistentPairHandlerStub();
		_persistentValuesHandler.registerPersistentPairHandler(persistentPairHandler);

		assertSame(persistentPairHandler,
				_persistentValuesHandler.getPersistentValueTypeHandler(
						IPair.class.getCanonicalName()));
	}
}
